package br.com.fiaplanchespayment.bdd;

import br.com.fiaplanchespayment.domain.enums.OrderStatus;
import br.com.fiaplanchespayment.infra.repository.PostGresPaymentRepository;
import br.com.fiaplanchespayment.infra.repository.entity.PaymentEntity;
import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.dtos.RequestProductDto;
import br.com.fiaplanchesproduct.domain.enums.Category;
import br.com.fiaplanchesproduct.infra.repository.PostGresProductRepository;
import br.com.fiaplanchesproduct.infra.repository.entity.ProductEntity;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StepDefinition {

    @Autowired
    private PostGresPaymentRepository paymentRepository;

    private Response response;

    private ProductDto productDto;

    private ProductDto secondProductDto;

    private ProductDto newProductDto;

    private final String ENDPOINT = "http://localhost:8082/v1/product";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost:" + 8082;
        paymentRepository.deleteAll();

        System.out.println("Including Data.....");

        PaymentEntity p1 = new PaymentEntity(1L,
                1L,OrderStatus.PAGAMENTO_APROVADO, new BigDecimal("12.00"), "Cartão", LocalDateTime.now());
        PaymentEntity p2 = new PaymentEntity(2L,
                2L,OrderStatus.PAGAMENTO_REJEITADO, new BigDecimal("20.00"), "Dinheiro", LocalDateTime.now());
        PaymentEntity p3 = new PaymentEntity(3L,
                3L,OrderStatus.PAGAMENTO_CANCELADO, new BigDecimal("32.00"), "Pix", LocalDateTime.now());
        PaymentEntity p4 = new PaymentEntity(4L,
                4L,OrderStatus.PAGAMENTO_APROVADO, new BigDecimal("40.00"), "Dinheiro", LocalDateTime.now());

        paymentRepository.saveAll(List.of(p1, p2, p3, p4));

        System.out.println("data Included.....");
        System.out.println("Id of Objects Included: " + p1.getId() + ", " + p2.getId() + ", " + p3.getId() + ", " + p4.getId() );
    }

    @Dado("o produto {word} de Id {int} e preco {double} e Categoria {word}")
    public void oProdutoHamburguerDeIdEPrecoECategoriaLANCHE(String produto, int id, double preco, String categoria ) {
        productDto = new ProductDto((long) id, produto, new BigDecimal(preco), Category.valueOf(categoria));
    }

    @Quando("for realizada a chamada no endpoint de registro de produto")
    public void forRealizadaAChamadaNoEndpointDeRegistroDeProduto() {

        response = given()
                .body(new RequestProductDto(productDto.nomeProduto(), productDto.preco(), productDto.category()))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post(ENDPOINT + "/register");

        response.then().assertThat().statusCode(HttpStatus.CREATED.value());
    }

    @Quando("for realizada a chamada no endpoint de busca de produto por categoria")
    public void forRealizadaAChamadaNoEndpointDeBuscaDeProdutoPorCategoria() {

        var url = ENDPOINT + "/find/" + productDto.toProduct().getCategory();

        response =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .when()
                        .get(url);

        System.out.println(url);

        response.then().assertThat().statusCode(HttpStatus.OK.value());
    }

    @Entao("o produto deve ser localizado com sucesso na base")
    public void oProdutoDeveSerLocalizadoComSucessoNaBase() {

        List<ProductEntity> productEntityList = paymentRepository.findByCategory(productDto.category());

        var product = productEntityList.stream().filter(x -> x.getCategory() == productDto.category()).findFirst();

        System.out.println(product.get().getCategory() + " Id = " + product.get().getId().toString());

        productDto.toProduct().setId(product.get().getId());
        productDto = new ProductDto(product.get().getId(), productDto.nomeProduto(), productDto.preco(), productDto.category());

        System.out.println(productDto.category() + " Id novo = " + productDto.id());

        assertEquals(productDto.category(), product.get().getCategory());
    }

    @Quando("for realizada a chamada no endpoint de atualizar produto")
    public void forRealizadaAChamadaNoEndpointDeAtualizarProduto() {

        var url = ENDPOINT + "/update/" + productDto.id();

        System.out.println("Printando url" + url);

        response =
                given()
                        .body(new RequestProductDto(newProductDto.nomeProduto(), newProductDto.preco(), newProductDto.category()))
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .when()
                        .put(url);

        response.then().assertThat().statusCode(HttpStatus.OK.value());
    }

    @E("o produto será alterado para {word} de Preco {double} e Categoria {word}")
    public void oProdutoSeráAlteradoParaCocaDePrecoECategoriaBEBIDA(String produto, double preco, String categoria) {
        newProductDto = new ProductDto(productDto.id(), produto, new BigDecimal(preco), Category.valueOf(categoria));

    }

    @Dado("o produto {word} e preco {double} e Categoria {word}")
    public void oProdutoCocaEPrecoECategoriaBEBIDA(String produto, double preco, String categoria) {
        productDto = new ProductDto(1L, produto, new BigDecimal(preco), Category.valueOf(categoria));
    }

    @Quando("for realizada a chamada no endpoint de deletar produto")
    public void forRealizadaAChamadaNoEndpointDeDeletarProduto() {

        var url = ENDPOINT + "/delete/" + productDto.id();

        System.out.println("Printando url" + url);

        response =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .when()
                        .delete(url);

        response.then().assertThat().statusCode(HttpStatus.OK.value());
    }

    @Entao("o produto deve não deve ser localizado na base")
    public void oProdutoDeveNãoDeveSerLocalizadoComSucessoNaBase() {

        Optional<ProductEntity> productResult = paymentRepository.findById(productDto.id());

        assertTrue(productResult.isEmpty());

    }

    @E("o segundo produto {word} de Id {int} e preco {double} e Categoria {word}")
    public void oSegundoProdutoHamburguerDeIdEPrecoECategoriaLANCHE(String produto, int id, double preco, String categoria ) {
        secondProductDto = new ProductDto((long) id, produto, new BigDecimal(preco), Category.valueOf(categoria));
    }

    @Quando("for realizada a chamada no endpoint de busca de produto por ids")
    public void forRealizadaAChamadaNoEndpointDeBuscaDeProdutoPorIds() {

        var url = ENDPOINT + "/find?ids=" + productDto.id() + "," + secondProductDto.id();

        System.out.println("Printando url: " + url);

        response =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .when()
                        .get(url);

        response.then().assertThat().statusCode(HttpStatus.OK.value());
    }

    @Dado("o segundo produto {word} e preco {double} e Categoria {word}")
    public void oSegundoProdutoCocaEPrecoECategoriaBEBIDA(String produto, double preco, String categoria) {
        secondProductDto = new ProductDto(1L, produto, new BigDecimal(preco), Category.valueOf(categoria));
    }

    @E("o segundo produto deve ser localizado com sucesso na base")
    public void oSegundoProdutoDeveSerLocalizadoComSucessoNaBase() {
        List<ProductEntity> productEntityList = paymentRepository.findByCategory(secondProductDto.category());

        var product = productEntityList.stream().filter(x -> x.getCategory() == secondProductDto.category()).findFirst();

        System.out.println(product.get().getCategory() + "Segundo produto Id = " + product.get().getId().toString());

        secondProductDto.toProduct().setId(product.get().getId());
        secondProductDto = new ProductDto(product.get().getId(), secondProductDto.nomeProduto(), secondProductDto.preco(), secondProductDto.category());

        System.out.println(secondProductDto.category() + "Segundo produto Id novo = " + secondProductDto.id());

        assertEquals(secondProductDto.category(), product.get().getCategory());
    }
}

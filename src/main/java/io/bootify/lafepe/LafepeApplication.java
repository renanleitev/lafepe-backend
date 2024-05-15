package io.bootify.lafepe;

import io.bootify.lafepe.domain.Estoque;
import io.bootify.lafepe.domain.Produto;
import io.bootify.lafepe.domain.Registro;
import io.bootify.lafepe.repos.EstoqueRepository;
import io.bootify.lafepe.repos.ProdutoRepository;
import io.bootify.lafepe.repos.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;


@SpringBootApplication
public class LafepeApplication implements CommandLineRunner {

    @Autowired
    ProdutoRepository databaseProduto;

    @Autowired
    EstoqueRepository databaseEstoque;

    @Autowired
    RegistroRepository databaseRegistro;

    public static void main(final String[] args) {
        SpringApplication.run(LafepeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Usar apenas na primeira vez para inserir os dados (por padrão == true)
        Boolean firstLoad = true;

        // Produto

        String[] codigoLista = {
                "20086",
                "20085",
                "20090",
                "20091",
                "20087",
                "20088",
                "20100",
                "21138",
                "21153",
                "20101",
        };

        String[] nomeLista = {
                "Paracetamol",
                "Ibuprofeno",
                "Omeprazol",
                "Dipirona",
                "Amoxicilina",
                "Diclofenaco",
                "Ranitidina",
                "Atorvastatina",
                "Dexametasona",
                "Metformina"
        };

        String[] fabricanteLista = {
                "Pfizer",
                "Roche",
                "Johnson & Johnson",
                "Bayer",
                "AstraZeneca",
                "Pfizer",
                "Roche",
                "Johnson & Johnson",
                "Bayer",
                "AstraZeneca",
        };

        double[] precoUnitarioLista = {
                10.50,
                15.75,
                20.20,
                5.80,
                30.45,
                8.90,
                12.30,
                25.60,
                18.95,
                22.15
        };

        // Estoque

        String[] loteLista = {
                "02254",
                "02261",
                "02298",
                "02301",
                "02307",
                "02314",
                "02325",
                "02391",
                "02392",
                "02013",
        };

        Integer[] quantidadeLista = {
                464400,
                5350,
                6241927,
                682819,
                19061317,
                4000000,
                1250000,
                765764,
                3403734,
                6789968,
        };

        String[] unidadeLista = {
                "MH",
                "UND",
                "KG",
                "L",
                "MH",
                "UND",
                "KG",
                "L",
                "KG",
                "MH",
        };

        Integer[] quarentenaLista = {
                4644,
                53,
                624,
                682,
                1906,
                400,
                125,
                7657,
                3403,
                6789,
        };

        LocalDate[] validadeLista = {
                LocalDate.parse("2024-05-03"),
                LocalDate.parse("2024-05-13"),
                LocalDate.parse("2024-06-25"),
                LocalDate.parse("2024-07-03"),
                LocalDate.parse("2024-08-12"),
                LocalDate.parse("2024-12-25"),
                LocalDate.parse("2025-01-08"),
                LocalDate.parse("2025-04-15"),
                LocalDate.parse("2025-07-22"),
                LocalDate.parse("2025-12-20"),
        };

        String[] descricaoLista = {
                "Analgésico e antipirético utilizado para aliviar a dor e a febre.",
                "Anti-inflamatório não esteroide usado para tratar a dor, a febre e a inflamação.",
                "Inibidor da bomba de prótons usado para tratar úlceras gástricas, azia e refluxo ácido.",
                "Analgésico e antipirético utilizado para aliviar a dor e a febre.",
                "Antibiótico do grupo das penicilinas, usado no tratamento de infecções bacterianas.",
                "Anti-inflamatório não esteroide usado para tratar a dor, a inflamação e a febre.",
                "Antagonista dos receptores de histamina H2 utilizado para tratar úlceras gástricas e refluxo gastroesofágico.",
                "Inibidor da enzima HMG-CoA redutase, utilizado para diminuir os níveis de colesterol e prevenir doenças cardiovasculares.",
                "Corticosteroide utilizado para reduzir inflamações e suprimir o sistema imunológico.",
                "Antidiabético oral utilizado para tratar o diabetes mellitus tipo 2."
        };

        // Registro

        Integer[] entradaLista = {
                46400,
                530,
                62427,
                6819,
                190617,
                123,
                5217,
                7657,
                3403,
                67899,
        };

        Integer[] saidaLista = {
                564479,
                45350,
                927653,
                82819,
                9061317,
                400000,
                1750000,
                75764,
                343734,
                679968,
        };

        Integer[] saldoLista = new Integer[10];

        for(int i = 0; i < quantidadeLista.length; i++) {
            saldoLista[i] = ((quantidadeLista[i] + quarentenaLista[i] + entradaLista[i]) - saidaLista[i]);
        }

        LocalDate[] dataLista = {
                LocalDate.parse("2024-01-01"),
                LocalDate.parse("2024-02-01"),
                LocalDate.parse("2024-03-01"),
                LocalDate.parse("2024-04-01"),
                LocalDate.parse("2024-05-01"),
                LocalDate.parse("2024-06-01"),
                LocalDate.parse("2024-07-01"),
                LocalDate.parse("2024-08-01"),
                LocalDate.parse("2024-09-01"),
                LocalDate.parse("2024-10-01"),
        };

        if (firstLoad) {
            for(int i = 0; i < codigoLista.length; i++) {
                // Produto
                Produto produto = new Produto();
                produto.setCodigo(codigoLista[i]);
                produto.setNome(nomeLista[i]);
                produto.setFabricante(fabricanteLista[i]);
                produto.setPrecoUnitario(precoUnitarioLista[i]);
                databaseProduto.save(produto);
                // Estoque
                Estoque estoque = new Estoque();
                estoque.setLote(loteLista[i]);
                estoque.setQuantidade(quantidadeLista[i]);
                estoque.setUnidade(unidadeLista[i]);
                estoque.setQuarentena(quarentenaLista[i]);
                estoque.setSaldoOriginal(saldoLista[i]);
                estoque.setSaldoAtual(saldoLista[i]);
                estoque.setValidade(validadeLista[i]);
                estoque.setDescricao(descricaoLista[i]);
                estoque.setProduto(produto);
                databaseEstoque.save(estoque);
                // Registro
                Registro registro = new Registro();
                registro.setEntrada(entradaLista[i]);
                registro.setSaida(saidaLista[i]);
                registro.setData(dataLista[i]);
                registro.setEstoque(estoque);
                databaseRegistro.save(registro);
            }
        }
    }

}

package Inova.Grupo.BoaVaga;

import Inova.Grupo.BoaVaga.model.StatusVaga;
import Inova.Grupo.BoaVaga.model.Vaga;
import Inova.Grupo.BoaVaga.repository.VagaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final VagaRepository vagaRepository;

    public DataLoader(VagaRepository vagaRepository) {
        this.vagaRepository = vagaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existem vagas para não duplicar
        if (vagaRepository.count() == 0) {
            System.out.println("Populando o banco de dados com vagas iniciais...");

            Vaga v1 = new Vaga();
            v1.setNumeroVaga("A1");
            v1.setStatus(StatusVaga.DISPONIVEL);

            Vaga v2 = new Vaga();
            v2.setNumeroVaga("A2");
            v2.setStatus(StatusVaga.DISPONIVEL);

            Vaga v3 = new Vaga();
            v3.setNumeroVaga("B1");
            v3.setStatus(StatusVaga.DISPONIVEL);

            vagaRepository.save(v1);
            vagaRepository.save(v2);
            vagaRepository.save(v3);

            System.out.println("Vagas criadas com sucesso!");
        }
    }
}
package tn.esprit.spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.repository.ContratRepository;

public class ContratServiceMock {

    @Mock
    private ContratRepository contratRepository;

    @InjectMocks
    private ContratService contratService;

    public ContratServiceMock() {
        // Initialisation des mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculerAncienneteContrat() {
        // Préparation des données de test
        Long referenceContrat = 1L;
        Date dateDebut = Date.from(LocalDate.of(2018, 10, 17)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        Contrat contrat = new Contrat();
        contrat.setReference(referenceContrat);
        contrat.setDateDebut(dateDebut);

        // Simulation du comportement du repository
        when(contratRepository.findById(referenceContrat)).thenReturn(Optional.of(contrat));

        // Appel de la méthode à tester
        int anciennete = contratService.calculerAncienneteContrat(referenceContrat);

        // Vérification des résultats
        assertEquals(6, anciennete); // L'ancienneté doit être de 6 ans
    }

    @Test
    public void testCalculerAncienneteContratContratNonTrouve() {
        // Simulation d'un contrat non trouvé
        Long referenceContrat = 2L;
        when(contratRepository.findById(referenceContrat)).thenReturn(Optional.empty());

        // Appel de la méthode à tester
        int anciennete = contratService.calculerAncienneteContrat(referenceContrat);

        // Vérification que l'ancienneté est 0 lorsque le contrat n'est pas trouvé
        assertEquals(0, anciennete);
    }
}

package tn.esprit.spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.repository.ContratRepository;

class ContratServiceTest {

    @InjectMocks
    private ContratService contratService;

    @Mock
    private ContratRepository contratRepository;

    @BeforeEach
    void setUp() {
        // Initialisation des mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculerAncienneteContrat() {
        // Date de début du contrat : il y a 5 ans à partir de maintenant
        LocalDate dateDebut = LocalDate.now().minusYears(5);
        Date dateDebutConverted = Date.from(dateDebut.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Créer un contrat fictif
        Contrat contrat = new Contrat(dateDebutConverted, "CDI", 3000f);
        contrat.setReference(1L);

        // Simuler le comportement du repository pour renvoyer ce contrat
        when(contratRepository.findById(1L)).thenReturn(Optional.of(contrat));

        // Appeler la méthode à tester
        int anciennete = contratService.calculerAncienneteContrat(1L);

        // Vérifier que l'ancienneté est bien de 5 ans
        assertEquals(5, anciennete);
    }

    @Test
    void testCalculerAncienneteContratContratNonTrouve() {
        // Simuler le comportement du repository pour renvoyer un contrat vide (non trouvé)
        when(contratRepository.findById(1L)).thenReturn(Optional.empty());

        // Appeler la méthode à tester
        int anciennete = contratService.calculerAncienneteContrat(1L);

        // Vérifier que l'ancienneté est 0 si le contrat n'est pas trouvé
        assertEquals(0, anciennete);
    }
}

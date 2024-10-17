package tn.esprit.spring.services;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.repository.ContratRepository;

@Service
public class ContratService {

    @Autowired
    private ContratRepository contratRepository;

    public int calculerAncienneteContrat(Long referenceContrat) {
        Contrat contrat = contratRepository.findById(referenceContrat).orElse(null);
        if (contrat != null) {
            LocalDate dateDebut = contrat.getDateDebut().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(dateDebut, currentDate);
            return period.getYears(); // Retourne l'ancienneté en années
        }
        return 0; // Si le contrat n'est pas trouvé ou n'a pas de date de début
    }
}


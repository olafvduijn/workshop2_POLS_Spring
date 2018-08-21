package view;

import java.util.Scanner;
import Controllers.AdresController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import validator.Validator;

@Component
public class AdresMenu {

    private Scanner input = new Scanner(System.in);
    @Autowired
    private AdresController adresController;
    int klantId;
    @Autowired
    AdresMenu nieuwAdresMenu;

    public AdresMenu() {

    }
//    public AdresMenu(int klantId) {
//        this.klantId = klantId;
//        adresController.setKlant(klantId);
//        adresGegevensMenu();
//    }

    public void adresGegevensMenu(int klantId) {
        this.klantId = klantId;
        adresController.setKlant(klantId);
        boolean erIsEenFactuuradres = adresController.factuurAdresAanwezig();
        boolean erIseenBezorgadres = adresController.BezorgAdresAanwezig();

        System.out.println("POSTADRES: " + System.lineSeparator() + adresController.toonPostadres());
        if (erIsEenFactuuradres) {
            System.out.println("FACTUURADRES: " + System.lineSeparator() + adresController.toonFactuuradres());
        }
        if (erIseenBezorgadres) {
            System.out.println("BEZORGADRES: " + System.lineSeparator() + adresController.toonBezorgadres());
        }
        System.out.println("");
        System.out.println("Kies en type in wat u wilt doen?");

        int factuuradresWijziging = -2;
        int factuuradresVerwijdering = -2;
        int factuuradresToevoeging = -2;
        int bezorgadresWijziging = -2;
        int bezorgadresVerwijdering = -2;
        int bezorgadresToevoeging = -2;

        int menuNummer = 1;
        System.out.println("1: Wijzig postadres");
        if (erIsEenFactuuradres) {
            factuuradresWijziging = menuNummer + 1;
            menuNummer++;
            System.out.println("" + factuuradresWijziging + ": Wijzig factuuradres");
            factuuradresVerwijdering = menuNummer + 1;
            menuNummer++;
            System.out.println("" + factuuradresVerwijdering + ": Verwijder factuuradres");
        } else {
            factuuradresToevoeging = menuNummer + 1;
            menuNummer++;
            System.out.println("" + factuuradresToevoeging + ": Voeg factuuradres toe");
        }
        if (erIseenBezorgadres) {
            bezorgadresWijziging = menuNummer + 1;
            menuNummer++;
            System.out.println("" + bezorgadresWijziging + ": Wijzig bezorgadres");
            bezorgadresVerwijdering = menuNummer + 1;
            menuNummer++;
            System.out.println("" + bezorgadresVerwijdering + ": Verwijder bezorgadres");
        } else {
            bezorgadresToevoeging = menuNummer + 1;
            menuNummer++;
            System.out.println("" + bezorgadresToevoeging + ": Voeg bezorgadres toe");
        }
        System.out.println("0: Ga terug naar klantmenu");

        int actie = input.nextInt();
        if (actie == 1) {
            wijzigPostadres();
        } else if (actie == factuuradresWijziging) {
            wijzigFactuuradres();
        } else if (actie == factuuradresVerwijdering) {
            verwijderFactuuradres();
        } else if (actie == factuuradresToevoeging) {
            VoegFactuuradresToe();
        } else if (actie == bezorgadresWijziging) {
            wijzigBezorgadres();
        } else if (actie == bezorgadresVerwijdering) {
            verwijderBezorgadres();
        } else if (actie == bezorgadresToevoeging) {
            VoegBezorgadresToe();
        } else if (actie == 0) {

        } else {
            System.out.println("Ongeldige keuze");
        }

    }

    private void VoegBezorgadresToe() {
        input.nextLine();
        String straatnaam = "";
        while (true) {
            System.out.println("Geef de straatnaam van het adres op");
            straatnaam = input.nextLine();
            if ((Validator.isNaamValid(straatnaam))) {
                break;
            }
        }
        System.out.println("Geef het huisnummer van het adres op");
        int huisnummer = input.nextInt();
        input.nextLine();
        System.out.println("Heeft het adres een toevoeging op het huisnummer?");
        System.out.println("1: ja");
        System.out.println("2: nee");
        String toevoeging = null;
        int toevoegingKeuze = input.nextInt();
        input.nextLine();
        if (toevoegingKeuze == 1) {
            System.out.println("Geef de toevoeging op het huisnummer op");
            toevoeging = input.nextLine();
        } else if (toevoegingKeuze == 2) {
        } else {
            System.out.println("Ongelding keuze, er wordt geen toevoeging opgeslagen");
        }
        String postcode = "";
        while (true) {
            System.out.println("Geef de postcode van het adres op");
            postcode = input.nextLine();
            if ((Validator.postcodeIsValid(postcode))) {
                break;
            }
        }
        String plaats = "";
        while (true) {
            System.out.println("Geef de plaats van het adres op");
            plaats = input.nextLine();
            if ((Validator.isNaamValid(plaats))) {
                break;
            }
        }
        adresController.maakBezorgAdres(straatnaam, huisnummer, toevoeging, postcode, plaats);
//        AdresMenu nieuwAdresMenu = new AdresMenu(klantId);
        nieuwAdresMenu.adresGegevensMenu(klantId);
    }

    private void VoegFactuuradresToe() {
        input.nextLine();
        String straatnaam = "";
        while (true) {
            System.out.println("Geef de straatnaam van het adres op");
            straatnaam = input.nextLine();
            if ((Validator.isNaamValid(straatnaam))) {
                break;
            }
        }
        System.out.println("Geef het huisnummer van het adres op");
        int huisnummer = input.nextInt();
        input.nextLine();
        System.out.println("Heeft het adres een toevoeging op het huisnummer?");
        System.out.println("1: ja");
        System.out.println("2: nee");
        String toevoeging = null;
        int toevoegingKeuze = input.nextInt();
        input.nextLine();
        if (toevoegingKeuze == 1) {
            System.out.println("Geef de toevoeging op het huisnummer op");
            toevoeging = input.nextLine();
        } else if (toevoegingKeuze == 2) {
        } else {
            System.out.println("Ongelding keuze, er wordt geen toevoeging opgeslagen");
        }
        String postcode = "";
        while (true) {
            System.out.println("Geef de postcode van het adres op");
            postcode = input.nextLine();
            if ((Validator.postcodeIsValid(postcode))) {
                break;
            }
        }
        String plaats = "";
        while (true) {
            System.out.println("Geef de plaats van het adres op");
            plaats = input.nextLine();
            if ((Validator.isNaamValid(plaats))) {
                break;
            }
        }
        adresController.maakFactuurAdres(straatnaam, huisnummer, toevoeging, postcode, plaats);
//        AdresMenu nieuwAdresMenu = new AdresMenu(klantId);
        nieuwAdresMenu.adresGegevensMenu(klantId);
    }

    private void verwijderBezorgadres() {
        adresController.setBezorgadres();
        adresController.verwijderAdres();
//        AdresMenu nieuwAdresMenu = new AdresMenu(klantId);
        nieuwAdresMenu.adresGegevensMenu(klantId);
    }

    private void verwijderFactuuradres() {
        adresController.setFactuuradres();
        adresController.verwijderAdres();
//        AdresMenu nieuwAdresMenu = new AdresMenu(klantId);
        nieuwAdresMenu.adresGegevensMenu(klantId);
    }

    private void wijzigBezorgadres() {
        input.nextLine();
        adresController.setBezorgadres();
        System.out.println("Huidig BEZORGADRES:");
        System.out.println(adresController.toonBezorgadres());
        wijzigAdres();

        int actie = input.nextInt();
        input.nextLine();
        switch (actie) {
            case 1:
                wijzigStraat();
                break;
            case 2:
                wijzigHuisnummer();
                break;
            case 3:
                wijzigToevoeging();
                break;
            case 4:
                wijzigPostcode();
                break;
            case 5:
                wijzigPlaats();
                break;
            case 6:
                wijzigAlles();
        }
//        AdresMenu nieuwAdresMenu = new AdresMenu(klantId);
        nieuwAdresMenu.adresGegevensMenu(klantId);
    }

    private void wijzigFactuuradres() {
        adresController.setFactuuradres();
        System.out.println("Huidig FACTUURADRES:");
        System.out.println(adresController.toonFactuuradres());
        wijzigAdres();

        int actie = input.nextInt();
        input.nextLine();
        switch (actie) {
            case 1:
                wijzigStraat();
                break;
            case 2:
                wijzigHuisnummer();
                break;
            case 3:
                wijzigToevoeging();
                break;
            case 4:
                wijzigPostcode();
                break;
            case 5:
                wijzigPlaats();
                break;
            case 6:
                wijzigAlles();
        }
//        AdresMenu nieuwAdresMenu = new AdresMenu(klantId);
        nieuwAdresMenu.adresGegevensMenu(klantId);
    }

    private void wijzigPostadres() {
        adresController.setPostadres();
        System.out.println("Huidig POSTADRES:");
        System.out.println(adresController.toonPostadres());
        wijzigAdres();

        int actie = input.nextInt();
        input.nextLine();
        switch (actie) {
            case 1:
                wijzigStraat();
                break;
            case 2:
                wijzigHuisnummer();
                break;
            case 3:
                wijzigToevoeging();
                break;
            case 4:
                wijzigPostcode();
                break;
            case 5:
                wijzigPlaats();
                break;
            case 6:
                wijzigAlles();
        }
//        AdresMenu nieuwAdresMenu = new AdresMenu(klantId);
        nieuwAdresMenu.adresGegevensMenu(klantId);
    }

    private void wijzigAdres() {
        System.out.println("");
        System.out.println("Wat wilt u aanpassen?");
        System.out.println("1: Enkel straatnaam aanpassen");
        System.out.println("2: Enkel huisnummer aanpassen");
        System.out.println("3: Enkel toevoeging aanpassen");
        System.out.println("4: Enkel postcode aanpassen");
        System.out.println("5: Enkel plaats aanpassen");
        System.out.println("6: Alles aanpassen");
        //System.out.println("");
        //System.out.println("-1: Ga terug naar klantmenu");
        //System.out.println("0: Log uit");
    }

    private void wijzigStraat() {
        String nieuweStraat = "";
        while (true) {
            System.out.println("Geef nieuwe straatnaam in");
            nieuweStraat = input.nextLine();
            if ((Validator.isNaamValid(nieuweStraat))) {
                break;
            }
        }
        adresController.wijzigStraat(nieuweStraat);
        System.out.println("Straatnaam aangepast!");
    }

    private void wijzigHuisnummer() {
        System.out.println("Geef nieuwe huisnummer in");
        int nieuwHuisnummer = input.nextInt();
        input.nextLine();
        adresController.wijzigHuisnummer(nieuwHuisnummer);
        System.out.println("Huisnummer aangepast!");
    }

    private void wijzigToevoeging() {
        System.out.println("Geef nieuwe toevoeging in (typ verwijder om toevoeging te verwijderen");
        String nieuweToevoeging = input.nextLine();
        if (nieuweToevoeging.equals("verwijder")) {
            adresController.wijzigToevoeging(null);
        } else {
            adresController.wijzigToevoeging(nieuweToevoeging);
        }
        System.out.println("Toevoeging aangepast!");
    }

    private void wijzigPostcode() {
        String nieuwePostcode = "";
        while (true) {
            System.out.println("Geef de nieuwe postcode van het adres op");
            nieuwePostcode = input.nextLine();
            if ((Validator.postcodeIsValid(nieuwePostcode))) {
                break;
            }
        }
        adresController.wijzigPostcode(nieuwePostcode);
        System.out.println("Postcode aangepast!");
    }

    private void wijzigPlaats() {
        String nieuwePlaats = "";
        while (true) {
            System.out.println("Geef nieuwe nieuwePlaats in");
            nieuwePlaats = input.nextLine();
            if ((Validator.isNaamValid(nieuwePlaats))) {
                break;
            }
        }
        adresController.wijzigPlaats(nieuwePlaats);
        System.out.println("Plaatsnaam aangepast!");
    }

    private void wijzigAlles() {
        wijzigStraat();
        wijzigHuisnummer();
        wijzigToevoeging();
        wijzigPostcode();
        wijzigPlaats();
    }
}

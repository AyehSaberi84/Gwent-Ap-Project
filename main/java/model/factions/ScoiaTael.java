package model.factions;

import javafx.scene.image.Image;
import model.Ability;
import model.Card;
import model.commanders.Commander;
import model.commanders.northern_realms.*;
import model.commanders.scoia_tael.*;

import java.util.ArrayList;
import java.util.Objects;

public class ScoiaTael extends Faction{
    private static ArrayList<Card> allCards = new ArrayList<>();
    public ScoiaTael(){
        super.setName("ScoiaTeal");
        super.setFlagImage(new Image(Objects.requireNonNull(Faction.class.getResourceAsStream("/Images/ScoiaTael/deck_shield_scoiatael.png"))));
        super.setCardImage(new Image(String.valueOf(Faction.class.getResource("/Images/ScoiaTael/faction_scoiatael.jpg"))));
        setAllCards();
    }


    public static Card makeCardWithName(String cardName) {
        new ScoiaTael();
        for (Card card : allCards) {
            if (card.getName().equals(cardName)) return new Card(card);
        }
        return null;
    }

    @Override
    public Commander makeCommanderByName(String commanderName) {
        return switch (commanderName) {
            case "Daisy Of The Valley" -> new DaisyOfTheValley();
            case "Hope Of The AenSeidhe" -> new HopeOfTheAenSeidhe();
            case "Pureblood Elf" -> new PurebloodElf();
            case "Queen Of DolBlathanna" -> new QueenOfDolBlathanna();
            case "The Beautiful" -> new TheBeautiful();
            default -> null;
        };
    }


    private void setAllCards (){
        if (!allCards.isEmpty()) allCards = new ArrayList<>();

        Card elvenSkirmisher = new Card("Elven Skirmisher",this,"Ranged" ,false , 2 , 3 , Ability.MUSTER,"/ScoiaTael/scoiatael_elf_skirmisher.jpg");
        allCards.add(elvenSkirmisher);

        Card iorveth = new Card("Iorveth",this,"Ranged",true,10,1,null,"/ScoiaTael/scoiatael_iorveth.jpg");
        allCards.add(iorveth);

        Card yaevinn = new Card("Yaevinn",this,"Agile",false,6,1, null,"/ScoiaTael/scoiatael_yaevinn.jpg");
        allCards.add(yaevinn);

        Card ciaranaep = new Card("Ciaran aep",this,"Agile",false,3,1, null,"/ScoiaTael/scoiatael_ciaran.jpg");
        allCards.add(ciaranaep);

        Card dennisCranmer = new Card("Dennis Cranmer",this,"Close Combat",false,6,1, null,"/ScoiaTael/scoiatael_dennis.jpg");
        allCards.add(dennisCranmer);

        Card dolBlathannaScout = new Card("Dol Blathanna Scout",this,"Agile",false,6,3,null,"/ScoiaTael/scoiatael_dol_infantry.jpg");
        allCards.add(dolBlathannaScout);

        Card dolBlathannaArcher = new Card("Dol Blathanna Archer",this,"Ranged",false,4,1,null,"/ScoiaTael/scoiatael_dol_archer.jpg");
        allCards.add(dolBlathannaArcher);

        Card dwarvenSkirmisher = new Card("Dwarven Skirmisher",this,"Close Combat",false,3,3, Ability.MUSTER,"/ScoiaTael/scoiatael_dwarf.jpg");
        allCards.add(dwarvenSkirmisher);

        Card filavandrel = new Card("Filavandrel",this,"Agile",false,6,1,null,"/ScoiaTael/scoiatael_filavandrel.jpg");
        allCards.add(filavandrel);

        Card havekarHealer = new Card("Havekar Healer",this,"Ranged",false,0,3, Ability.MEDIC,"/ScoiaTael/scoiatael_havekar_nurse.jpg");
        allCards.add(havekarHealer);

        Card havekarSmuggler = new Card("Havekar Smuggler",this,"Close Combat",false,5,3, Ability.MUSTER,"/ScoiaTael/scoiatael_havekar_support.jpg");
        allCards.add(havekarSmuggler);

        Card idaEmeanaep = new Card("Ida Emean aep",this,"Ranged",false,6,1 ,null,"/ScoiaTael/scoiatael_ida.jpg");
        allCards.add(idaEmeanaep);

        Card riordain = new Card("Riordain",this,"Ranged",false,1,1,null,"/ScoiaTael/scoiatael_riordain.jpg");
        allCards.add(riordain);

        Card toruviel = new Card("Toruviel",this,"Ranged",false,2,1,null,"/ScoiaTael/scoiatael_toruviel.jpg");
        allCards.add(toruviel);

        Card vriheddBrigadeRecruit = new Card("Vrihedd Brigade Recruit",this,"Ranged",false,4,1,null,"/ScoiaTael/scoiatael_vrihedd_cadet.jpg");
        allCards.add(vriheddBrigadeRecruit);

        Card mahakamanDefender = new Card("Mahakaman Defender",this,"Close Combat",false,5,5,null,"/ScoiaTael/scoiatael_mahakam.jpg");
        allCards.add(mahakamanDefender);

        Card vriheddBrigadeVeteran = new Card("Vrihedd Brigade Veteran",this,"Agile",false,5,2,null,"/ScoiaTael/scoiatael_vrihedd_brigade.jpg");
        allCards.add(vriheddBrigadeVeteran);

        Card milva = new Card("Milva",this,"Ranged",false,10,1, Ability.MORAL_BOOST,"/ScoiaTael/scoiatael_milva.jpg");
        allCards.add(milva);

        Card ceasenthessis = new Card("Seasenthessis",this,"Ranged",true,10,1 , null,"/ScoiaTael/scoiatael_saskia.jpg");
        allCards.add(ceasenthessis);

        Card schirru = new Card("Schirru",this,"Siege",false,8,1, Ability.SCORCH,"/ScoiaTael/scoiatael_schirru.jpg");
        allCards.add(schirru);

        Card barclayEls = new Card("Barclay Els",this,"Agile",false,6,1,null,"/ScoiaTael/scoiatael_barclay.jpg");
        allCards.add(barclayEls);

        Card eithne = new Card("Eithne",this,"Ranged",true,10,1, null,"/ScoiaTael/scoiatael_eithne.jpg");
        allCards.add(eithne);

        Card isengrimFaoiltiarna = new Card("Isengrim Faoiltiarna",this,"Close Combat",true,10,1, Ability.MORAL_BOOST,"/ScoiaTael/scoiatael_isengrim.jpg");
        allCards.add(isengrimFaoiltiarna);

    }
    public boolean validName (String cardName){
        for (Card card : allCards)
            if (card.getName().equals(cardName)) return true;
        return false;
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }
}

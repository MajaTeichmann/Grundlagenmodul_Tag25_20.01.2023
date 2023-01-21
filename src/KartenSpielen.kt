// eigenes Kartenspiel

val standartKartendeck: List<String> = listOf(
    "Pik Ass", "Pik Koenig", "Pik Dame", "Pik Bube", "Pik 10", "Pik 9",
    "Pik 8", "Pik 7", "Pik 6", "Pik 5", "Pik 4", "Pik 3", "Pik 2",
    "Kreuz Ass", "Kreuz Koenig", "Kreuz Dame", "Kreuz Bube", "Kreuz 10", "Kreuz 9",
    "Kreuz 8", "Kreuz 7", "Kreuz 6", "Kreuz 5", "Kreuz 4", "Kreuz 3", "Kreuz 2",
    "Herz Ass", "Herz Koenig", "Herz Dame", "Herz Bube", "Herz 10", "Herz 9",
    "Herz 8", "Herz 7", "Herz 6", "Herz 5", "Herz 4", "Herz 3", "Herz 2",
    "Karo Ass", "Karo Koenig", "Karo Dame", "Karo Bube", "Karo 10", "Karo 9",
    "Karo 8", "Karo 7", "Karo 6", "Karo 5", "Karo 4", "Karo 3", "Karo 2",
)

// todo: überprüfung der entscheidung ist irgendwo falsch
//  bei richtig, wird direkt die nächste karte gezogen (soll sie nicht xD)
//  es wird keine neue random karte gezogen

val maxRunden = standartKartendeck.size
var rundenAnzahl = 0
var witzbold = 0
var unserKartenDeck = standartKartendeck.shuffled().toMutableList()
var leben = 1
var gespielteRunden = 0
var zufaelligeKarte = ""
var bereitsGespielteKarten = mutableListOf<String>()
var eingabe = 0
var index = 0
fun main() {
    spiel()
}

fun spiel(){
    //EINSTELLUNGEN
    //Begrüßung und evtl. Spielregeln
    begruessung()

    //Lege Rundenanzahl fest
    rundenAnzahlFestlegen()

    //Karten werden gemischt
    mischen()

    //EIGENTLICHES SPIEL
    hoeherTieferGleich()
}

fun begruessung() {
    println("Herzlich Willkommen bei 'Höher, Tiefer oder Gleich'!")
    Thread.sleep(1000)
    println("Wollen Sie die Spielregeln sehen? [1] für JA, [2] für NEIN")
    eingabe = readln().toInt()
    if(eingabe == 1){
        spielregeln()
    }
}

fun spielregeln() {
    println(
        """
        Spielregeln:
        Das Spiel beginnt mit der ersten zufälligen Karte, die der Computer zieht.
        Nun musst du (der Spieler) raten, ob die nächste zufällige Karte Höher [1], Tiefer [2] oder Gleich [3] ist.
        Liegst du RICHTIG, darfst du weiter spielen,
        liegst du aber FALSCH, ist das Spiel beendet.
        Viel Glück!""".trimIndent()
    )
    Thread.sleep(1000)
}

fun rundenAnzahlFestlegen() {
    println(
        """
        Zunächst lege bitte fest, wieviele Runden du MAXIMAL spielen möchtest.
        Bedenke: Mehr als $maxRunden Runden sind nicht möglich!""".trimIndent()
    )
    rundenAnzahl = readln().toInt()
    if (rundenAnzahl in 1..maxRunden) {
        println("Vielen Dank, wir spielen nun maximal $rundenAnzahl Runden!")
    } else {
        while (witzbold < 1) {
            Thread.sleep(1000)
            println()
            println(
                "Exception in thread \"main\" java.lang.ArrayIndexOutOfBoundsException: Index ${rundenAnzahl} out of bounds for length ${standartKartendeck.size}\n" +
                        "\tat java.base/java.util.Arrays$ ArrayList.get(Arrays.java:4165)\n" +
                        "\tat KartenSpielenKt.main(KartenSpielen.kt:39)\n" +
                        "\tat KartenSpielenKt.main(KartenSpielen.kt)"
            )
            println()
            println("Process finished with exit code 1")
            Thread.sleep(5000)
            witzbold++
            println(
                """
                Hab dich nur veräppelt...
                Wir spielen jetzt eine [1] Spielrunde, du Witzbold!""".trimIndent()
            )
            rundenAnzahl = 1
        }
    }
    Thread.sleep(1000)
}

fun mischen(){
    println("Computer mischt die Spielkarten...")
    unserKartenDeck = standartKartendeck.shuffled().toMutableList()
}

fun hoeherTieferGleich() {
    leben = 1
    gespielteRunden = 0
    while (leben == 1) {
        if (gespielteRunden == 0 && index == 0) {
            gespielteRunden++
            println("Runde ${gespielteRunden} von ${rundenAnzahl}")
            zufaelligeKarte = unserKartenDeck.random()
            println("Unsere Karte ist: $zufaelligeKarte [${kartenWert(zufaelligeKarte)}]")
            addToBereitsGespielteKarte_RemoveFromUnserKartenDeck()

            println("Ist die nächste zufällige Karte Höher [1], Tiefer [2] oder Gleich [3]?")
            eingabe = readln().toInt()
            zufaelligeKarte = unserKartenDeck.random()
            println("Unsere Karte ist: $zufaelligeKarte [${kartenWert(zufaelligeKarte)}]")
            println("${kartenWert(bereitsGespielteKarten[index])} vs. ${kartenWert(zufaelligeKarte)}")
            spielEntscheidung()
        }
        else if (gespielteRunden > 0 && gespielteRunden < rundenAnzahl) {
            gespielteRunden++
            println("Runde ${gespielteRunden} von ${rundenAnzahl}")
            println("$zufaelligeKarte [${kartenWert(zufaelligeKarte)}]")
            index++
            println("Ist die nächste zufällige Karte Höher [1], Tiefer [2] oder Gleich [3]?")
            eingabe = readln().toInt()
            addToBereitsGespielteKarte_RemoveFromUnserKartenDeck()
            zufaelligeKarte = unserKartenDeck.random()
            println("Unsere Karte ist: $zufaelligeKarte [${kartenWert(zufaelligeKarte)}]")
            addToBereitsGespielteKarte_RemoveFromUnserKartenDeck()
            println(bereitsGespielteKarten)
            println("${kartenWert(bereitsGespielteKarten[index])} vs. ${kartenWert(zufaelligeKarte)}")
            spielEntscheidung()
        }
        else if(gespielteRunden > rundenAnzahl){
            println("Maximale Spielrunden absolviert!")
            spielNeuStarten()
        }
    }
}
fun addToBereitsGespielteKarte_RemoveFromUnserKartenDeck(){
    bereitsGespielteKarten.add(zufaelligeKarte)
    unserKartenDeck.remove(zufaelligeKarte)
}

fun spielEntscheidung(){
    if(eingabe == 1){
        if(kartenWert(zufaelligeKarte) > kartenWert(bereitsGespielteKarten[index])){
            println("richtig!")
            addToBereitsGespielteKarte_RemoveFromUnserKartenDeck()
        }else{
            leben--
            falschGeraten()
        }
    }
    else if(eingabe == 2){
        if(kartenWert(zufaelligeKarte) < kartenWert(bereitsGespielteKarten[index])){
            println("richtig!")
            addToBereitsGespielteKarte_RemoveFromUnserKartenDeck()
        }else{
            leben--
            falschGeraten()
        }
    }
    else if(eingabe == 3){
        if(kartenWert(zufaelligeKarte) == kartenWert(bereitsGespielteKarten[index])){
            println("richtig!")
            addToBereitsGespielteKarte_RemoveFromUnserKartenDeck()
        }else{
            leben--
            falschGeraten()
        }
    }else{
        println("Du musst dich wohl vertippt haben. Versuche es erneut!")
        return spielEntscheidung()
    }
}

fun falschGeraten(){
    println("Das war leider falsch!")
    spielNeuStarten()
}

fun spielNeuStarten(){
    println("Neuer Versuch? [1] für ja, [2] für nein")
    eingabe = readln().toInt()
    if (eingabe == 1) {
        standartEinstellungen()
        main()
    } else {
        println("Auf Wiedersehen!")
    }
}

fun standartEinstellungen(){
    //Alle Voreinstellungen auf Standart
    rundenAnzahl = 0
    witzbold = 0
    unserKartenDeck = standartKartendeck.shuffled().toMutableList()
    leben = 1
    gespielteRunden = 0
    zufaelligeKarte = ""
    bereitsGespielteKarten = mutableListOf<String>()
    eingabe = 0
    index = 0
}

fun kartenWert(karte: String): Int {
    val kartenWert = karte.split(" ")[1]

    return when (kartenWert) {
        "2" -> 2
        "3" -> 3
        "4" -> 4
        "5" -> 5
        "6" -> 6
        "7" -> 7
        "8" -> 8
        "9" -> 9
        in listOf("10", "Koenig", "Dame", "Bube") -> 10
        "Ass" -> 11
        else -> 0
    }
}
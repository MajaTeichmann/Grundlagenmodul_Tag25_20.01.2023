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
var unserKartendeck = standartKartendeck.shuffled().toMutableList()
var bereitsGespielteKarten = mutableListOf<String>()
var index = 0
var entscheidung = 0
var leben = 1
var maxRunden = standartKartendeck.size
var spielRunden = 0
var gespielteRunden = 0
var zufaelligeKarte = unserKartendeck.random()

fun main() {
    begruessungUndSpielregeln()
    println()
    hoeherTieferOderGleich()
}

fun begruessungUndSpielregeln() {
    // begrüßung
    println("Herzlich Willkommen bei 'Höher, Tiefer oder Gleich'!")

    println()

    // Spielerklärung
    println(
        """
        Spielregeln:
        Das Spiel beginnt mit der ersten zufälligen Karte, die der Computer zieht.
        Nun musst du (der Spieler) raten, ob die nächste zufällige Karte Höher [1], Tiefer [2] oder Gleich [3] ist.
        Liegst du RICHTIG, darfst du weiter spielen,
        liegst du aber FALSCH, ist das Spiel beendet.
        Viel Glück!""".trimIndent()
    )
}

// überprüfung der entscheidung ist irgendwo falsch
// bei richtig, wird direkt die nächste karte gezogen (soll sie nicht xD)
// es wird keine neue random karte gezogen
fun hoeherTieferOderGleich() {

    println(
        """
        Zunächst lege bitte fest, wieviele Runden du MAXIMAL spielen möchtest.
        Bedenke: Mehr als $maxRunden Runden sind nicht möglich!""".trimIndent()
    )
    spielRunden = readln().toInt()
    if (spielRunden > 0) {
        println("Vielen Dank, wir spielen nun maximal $spielRunden Runden!")
    } else {
        var witzbold = 1
        while (witzbold <= 100) {
            println("ERROR!")
            witzbold++
        }
        Thread.sleep(3000)
        println(
            """
            Hab dich nur veräppelt ;)
            Wir spielen jetzt eine [1] Spielrunde, du Witzbold!""".trimIndent()
        )
        spielRunden = 1
    }

    // computer mischt kartendeck
    println("Computer mischt Spielkarten...")

    while (leben == 1 && gespielteRunden == 0 && index == 0) {
        // computer deckt 1. karte auf und zeigt Kartenwert
        zufaelligeKarte = unserKartendeck.random()
        println("Unsere erste Karte ist: $zufaelligeKarte [${kartenWert(zufaelligeKarte)}]")
        bereitsGespielteKarten.add(zufaelligeKarte)
        unserKartendeck.remove(zufaelligeKarte)
        println()

        // spieler hat auswahl, ob nächste karte höher oder tiefer ist
        println(
            """
            Nun bist du am Zug!
            Ist die nächste zufällige Karte Höher [1], Tiefer [2] oder Gleich [3]?""".trimIndent()
        )
        entscheidung = readln().toInt()
        println("Unsere nächste Karte ist: $zufaelligeKarte [${kartenWert(zufaelligeKarte)}]")
        spielerEntscheidung()

        println()

        unserKartendeck.remove(zufaelligeKarte)
        gespielteRunden++
        index++
        println()
    }
    while (leben == 1 && spielRunden >= gespielteRunden && spielRunden <= maxRunden) {
        // computer deckt nächste karte auf & zeigt Kartenwert
        println("Unsere nächste Karte ist: $zufaelligeKarte [${kartenWert(zufaelligeKarte)}]")
        println()
        println("Ist die nächste zufällige Karte Höher [1], Tiefer [2] oder Gleich [3]?")
        entscheidung = readln().toInt()
        spielerEntscheidung()
        println()
        println("${kartenWert(bereitsGespielteKarten[index])} vs. ${kartenWert(zufaelligeKarte)}")
        println()
        bereitsGespielteKarten.add(zufaelligeKarte)
        println()
        println(
            """
            Nun bist du am Zug!
            Ist die nächste zufällige Karte Höher [1], Tiefer [2] oder Gleich [3]?""".trimIndent()
        )
        spielerEntscheidung()
        gespielteRunden++
        index++
        println()
        println("zum testen: $bereitsGespielteKarten")
    }
    println(
        """
        Maximale Spielrunden absolviert!
        Erneut?""".trimIndent()
    )
    entscheidung = readln().toInt()
    if (entscheidung == 1) {
        hoeherTieferOderGleich()
    } else {
        println("Auf Wiedersehen!")
    }
}

fun spielerEntscheidung() {
    when (entscheidung) {
        1 -> {
            if (kartenWert(zufaelligeKarte) > kartenWert(bereitsGespielteKarten[index])) {
                println("Du hast richtig geraten!")
                bereitsGespielteKarten.add(zufaelligeKarte)
                unserKartendeck.remove(zufaelligeKarte)
            } else {
                falschGeraten()
            }
        }

        2 -> {
            if (kartenWert(zufaelligeKarte) < kartenWert(bereitsGespielteKarten[index])) {
                println("Du hast richtig geraten!")
                bereitsGespielteKarten.add(zufaelligeKarte)
                unserKartendeck.remove(zufaelligeKarte)
            } else {
                falschGeraten()
            }
        }

        3 -> {
            if (kartenWert(zufaelligeKarte) == kartenWert(bereitsGespielteKarten[index])) {
                println("Du hast richtig geraten!")
                bereitsGespielteKarten.add(zufaelligeKarte)
                unserKartendeck.remove(zufaelligeKarte)
            } else {
                falschGeraten()
            }
        }
    }
}

fun falschGeraten() {
    println("Das war leider falsch! Viel Glück beim nächsten Versuch!")
    leben--
    println()
    println("Neuer Versuch? [1] für ja, [2] für nein")
    entscheidung = readln().toInt()
    if (entscheidung == 1) {
        leben = 1
        hoeherTieferOderGleich()
    }
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
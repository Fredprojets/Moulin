package leJeu

import kotlin.random.Random.Default.nextInt

fun main() {

    val joueur1 = Joueur("X","AI", AI = false)
    val joueur2 = Joueur("AI","X", AI = true)

    // Phase 1
    Plateau.display()
    repeat(9){
        print("Placez un jeton: ")
        joueur1.jouer()
        joueur2.jouer()

        Plateau.display()
    }
}

class Jeton(var proprietaire:String = "o", var proteger:Boolean = false){
    override fun toString(): String {
        return proprietaire.first().toString()
    }

    fun toChar(): Char {
        return proprietaire.first()
    }
}

class Joueur(val nom:String,val adversaire:String, val AI:Boolean){
    var nbJetons = 9

    fun jouer():Boolean{
        return if (AI){
            var valide = false
            while (!valide) {
                val anneau = listOf("b", "m", "c")
                val choix = anneau[nextInt(3)] + (nextInt(8) + 1).toString()

                valide =Plateau.placerJeton(this, choix)
                if (valide) nbJetons--
            }
            valide

        } else{ //coups du joeur humain
            var valide = false
            while (!valide) {
                valide =Plateau.placerJeton(this,readLine()!!)
                if (!valide){
                    println("Coup non valide, entrez un coup valide")
                } else nbJetons--
            }
            valide
        }
    }
}

object Plateau {
    var b: MutableList<Jeton> = mutableListOf(Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton())
    var m: MutableList<Jeton> = mutableListOf(Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton())
    var c: MutableList<Jeton> = mutableListOf(Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton())

    fun placerJeton(joueur: Joueur,endroit:String):Boolean{
        val position = endroit.last().toInt() - 49
        when (endroit) {
            in "b1".."b8"-> if(b[position].toString() == "o") {
                b[position].proprietaire = joueur.nom
                checkMoulin(endroit,joueur)
            } else return false
            in "m1".."m8" -> if(m[position].toString() == "o") {
                m[position].proprietaire = joueur.nom
                checkMoulin(endroit,joueur)
            } else return false
            in "c1".."c8" -> if(c[position].toString() == "o") {
                c[position].proprietaire = joueur.nom
                checkMoulin(endroit,joueur)
            } else return false
            else -> return false
        }

        return true
    }

    fun checkMoulin(position:String, joueur: Joueur){
        when(position.first()){
            'b' -> when(position.last().toInt()-49){
                0 -> if (b[1].proprietaire == joueur.nom && b[2].proprietaire == joueur.nom) moulin(b[0],b[1],b[2],joueur.adversaire) else if
                        (b[3].proprietaire == joueur.nom && b[5].proprietaire == joueur.nom) moulin(b[0],b[13],b[5],joueur.adversaire)

                1 -> if (b[0].proprietaire == joueur.nom && b[2].proprietaire == joueur.nom) moulin(b[1],b[0],b[2],joueur.adversaire) else if
                        (m[1].proprietaire == joueur.nom && c[1].proprietaire == joueur.nom) moulin(b[1],m[1],c[1],joueur.adversaire)

                2 -> if (b[0].proprietaire == joueur.nom && b[1].proprietaire == joueur.nom) moulin(b[2],b[0],b[1],joueur.adversaire) else if
                        (b[4].proprietaire == joueur.nom && b[7].proprietaire == joueur.nom) moulin(b[2],b[4],b[7],joueur.adversaire)

                3 -> if (b[0].proprietaire == joueur.nom && b[5].proprietaire == joueur.nom) moulin(b[3],b[0],b[5],joueur.adversaire) else if
                        (m[3].proprietaire == joueur.nom && c[3].proprietaire == joueur.nom) moulin(b[3],m[3],c[3],joueur.adversaire)

                4 -> if (b[2].proprietaire == joueur.nom && b[7].proprietaire == joueur.nom) moulin(b[4],b[2],b[7],joueur.adversaire) else if
                        (m[4].proprietaire == joueur.nom && c[4].proprietaire == joueur.nom) moulin(b[4],m[4],c[4],joueur.adversaire)

                5 -> if (b[0].proprietaire == joueur.nom && b[3].proprietaire == joueur.nom) moulin(b[5],b[0],b[3],joueur.adversaire) else if
                        (b[6].proprietaire == joueur.nom && b[7].proprietaire == joueur.nom) moulin(b[5],b[6],b[7],joueur.adversaire)

                6 -> if (b[5].proprietaire == joueur.nom && b[7].proprietaire == joueur.nom) moulin(b[6],b[5],b[7],joueur.adversaire) else if
                        (m[6].proprietaire == joueur.nom && c[6].proprietaire == joueur.nom) moulin(b[6],m[6],c[6],joueur.adversaire)

                7 -> if (b[5].proprietaire == joueur.nom && b[6].proprietaire == joueur.nom) moulin(b[7],b[5],b[6],joueur.adversaire) else if
                        (b[2].proprietaire == joueur.nom && b[4].proprietaire == joueur.nom) moulin(b[7],b[2],b[4],joueur.adversaire)
            }
            'm' -> when(position.last().toInt()-49){
                0 -> if (m[1].proprietaire == joueur.nom && m[2].proprietaire == joueur.nom) moulin(m[0],m[1],m[2],joueur.adversaire) else if
                        (m[3].proprietaire == joueur.nom && m[5].proprietaire == joueur.nom) moulin(m[0],m[3],m[5],joueur.adversaire)

                1 -> if (m[0].proprietaire == joueur.nom && m[2].proprietaire == joueur.nom) moulin(m[1],m[0],m[2],joueur.adversaire) else if
                        (b[1].proprietaire == joueur.nom && c[1].proprietaire == joueur.nom) moulin(m[1],b[1],c[1],joueur.adversaire)

                2 -> if (m[0].proprietaire == joueur.nom && m[1].proprietaire == joueur.nom) moulin(m[2],m[0],m[1],joueur.adversaire) else if
                        (m[4].proprietaire == joueur.nom && m[7].proprietaire == joueur.nom) moulin(m[2],m[4],m[7],joueur.adversaire)

                3 -> if (m[0].proprietaire == joueur.nom && m[5].proprietaire == joueur.nom) moulin(m[3],m[0],m[5],joueur.adversaire) else if
                        (b[3].proprietaire == joueur.nom && c[3].proprietaire == joueur.nom) moulin(m[3],b[3],c[3],joueur.adversaire)

                4 -> if (m[2].proprietaire == joueur.nom && m[7].proprietaire == joueur.nom) moulin(m[4],m[2],m[7],joueur.adversaire) else if
                        (b[4].proprietaire == joueur.nom && c[4].proprietaire == joueur.nom) moulin(m[4],b[4],c[4],joueur.adversaire)

                5 -> if (m[0].proprietaire == joueur.nom && m[3].proprietaire == joueur.nom) moulin(m[5],m[0],m[3],joueur.adversaire) else if
                        (m[6].proprietaire == joueur.nom && m[7].proprietaire == joueur.nom) moulin(m[5],m[6],m[7],joueur.adversaire)

                6 -> if (m[5].proprietaire == joueur.nom && m[7].proprietaire == joueur.nom) moulin(m[6],m[5],m[7],joueur.adversaire) else if
                        (b[6].proprietaire == joueur.nom && c[6].proprietaire == joueur.nom) moulin(m[6],b[6],c[6],joueur.adversaire)

                7 -> if (m[5].proprietaire == joueur.nom && m[6].proprietaire == joueur.nom) moulin(m[7],m[5],m[6],joueur.adversaire) else if
                        (m[2].proprietaire == joueur.nom && m[4].proprietaire == joueur.nom) moulin(m[7],m[2],m[4],joueur.adversaire)
            }
            'c' -> when(position.last().toInt()-49){
                0 -> if (c[1].proprietaire == joueur.nom && c[2].proprietaire == joueur.nom) moulin(c[0],c[1],c[2],joueur.adversaire) else if
                        (c[3].proprietaire == joueur.nom && c[5].proprietaire == joueur.nom) moulin(c[0],c[3],c[5],joueur.adversaire)

                1 -> if (c[0].proprietaire == joueur.nom && c[2].proprietaire == joueur.nom) moulin(c[1],c[0],c[2],joueur.adversaire) else if
                        (m[1].proprietaire == joueur.nom && b[1].proprietaire == joueur.nom) moulin(c[1],m[1],b[1],joueur.adversaire)

                2 -> if (c[0].proprietaire == joueur.nom && c[1].proprietaire == joueur.nom) moulin(c[2],c[0],c[1],joueur.adversaire) else if
                        (c[4].proprietaire == joueur.nom && c[7].proprietaire == joueur.nom) moulin(c[2],c[4],c[7],joueur.adversaire)

                3 -> if (c[0].proprietaire == joueur.nom && c[5].proprietaire == joueur.nom) moulin(c[3],c[0],c[5],joueur.adversaire) else if
                        (m[3].proprietaire == joueur.nom && b[3].proprietaire == joueur.nom) moulin(c[3],m[3],b[3],joueur.adversaire)

                4 -> if (c[2].proprietaire == joueur.nom && c[7].proprietaire == joueur.nom) moulin(c[4],c[2],c[7],joueur.adversaire) else if
                        (m[4].proprietaire == joueur.nom && b[4].proprietaire == joueur.nom) moulin(c[3],m[4],b[4],joueur.adversaire)

                5 -> if (c[0].proprietaire == joueur.nom && c[3].proprietaire == joueur.nom) moulin(c[5],c[0],c[3],joueur.adversaire) else if
                        (c[6].proprietaire == joueur.nom && c[7].proprietaire == joueur.nom) moulin(c[5],c[6],c[7],joueur.adversaire)

                6 -> if (c[5].proprietaire == joueur.nom && c[7].proprietaire == joueur.nom) moulin(c[6],c[5],c[7],joueur.adversaire) else if
                        (m[6].proprietaire == joueur.nom && b[6].proprietaire == joueur.nom) moulin(c[6],m[6],b[6],joueur.adversaire)

                7 -> if (c[5].proprietaire == joueur.nom && c[6].proprietaire == joueur.nom) moulin(c[7],c[5],c[6],joueur.adversaire) else if
                        (c[2].proprietaire == joueur.nom && c[4].proprietaire == joueur.nom) moulin(c[7],c[2],c[4],joueur.adversaire)
            }
        }
    }

    private fun moulin(p1:Jeton,p2:Jeton,p3:Jeton,ennemi: String){
        p1.proteger = true
        p2.proteger = true
        p3.proteger = true
        println("Choisissez un jeton ennemi à prendre:")
        while(!enleverJeton(readLine()!!,ennemi)){
            println("Choisissez un jeton ennemi à prendre:")
        }
    }

    private fun enleverJeton(endroit: String, ennemi: String):Boolean {
        val position = endroit.last().toInt() - 49
        when (endroit) {
            in "b1".."b8"-> if(b[position].toChar() == ennemi.first() && !b[position].proteger) {
                b[position].proprietaire = "o"
            } else return false
            in "m1".."m8" -> if(m[position].toChar() == ennemi.first() && !m[position].proteger) {
                m[position].proprietaire = "o"
            } else return false
            in "c1".."c8" -> if(c[position].toChar() == ennemi.first() && !c[position].proteger) {
                c[position].proprietaire = "o"
            } else return false
            else -> return false
        }
        return true
    }

    fun display() {
        val tableau:List<String> = listOf(
                " "+b[0]+"-----"+b[1]+"-----"+b[2]+" ",
                " |     |     | ",
                " | "+m[0]+"---"+m[1]+"---"+m[2]+" | ",
                " | | "+c[0]+"-"+c[1]+"-"+c[2]+" | | ",
                " | | |   | | | ",
                " "+b[3]+"-"+m[3]+"-"+c[3]+"   "+c[4]+"-"+m[4]+"-"+b[4]+" ",
                " | | |   | | | ",
                " | | "+c[5]+"-"+c[6]+"-"+c[7]+" | | ",
                " | "+m[5]+"---"+m[6]+"---"+m[7]+" | ",
                " |     |     | ",
                " "+b[5]+"-----"+b[6]+"-----"+b[7]+" "
                )
        for (element in tableau){
            println(element)
        }
    }
}
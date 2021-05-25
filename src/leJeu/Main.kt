package leJeu

import kotlin.random.Random.Default.nextInt

fun main() {

    val joueur1 = Joueur("X","AI", AI = false)
    val joueur2 = Joueur("AI","X", AI = true)

    // Phase 1
    Plateau.display()
    repeat(9){
        print("Placez un jeton: ")
        joueur1.placer()
        joueur2.placer()

        Plateau.display()
    }
    //Phase 2
    while (!Plateau.victoire()){
        joueur1.bouger()
        if (Plateau.victoire()){
            println("Le joueur"+joueur1.nom+" a gagné la partie")
        }
        joueur2.bouger()
    }

}

object Plateau {

    // les cases extérieur vers intérieur (b,m,c) et
    // position hautGauche,hMilieu,hdroite,milieuGauche,mmilieu,mDroite,basGauche,bmilieu,bDroite(0..7)
    var b = listOf(Case("b0"), Case("b1"), Case("b2"), Case("b3"), Case("b4"), Case("b5"), Case("b6"), Case("b7"))
    var m = listOf(Case("m0"), Case("m1"), Case("m2"), Case("m3"), Case("m4"), Case("m5"), Case("m6"), Case("m7"))
    var c = listOf(Case("c0"), Case("c1"), Case("c2"), Case("c3"), Case("c4"), Case("c5"), Case("c6"), Case("c7"))

    init {// pour chaque case défini les cases adjacente et les cases qui peuvent faire un moulin
        b[0].adj = arrayOf(b[1],b[3],m[0]) ; b[0].moulinable = arrayOf(arrayOf(b[1],b[2]), arrayOf(b[3],b[5]), arrayOf(m[0],c[0]))
        b[1].adj = arrayOf(b[0],b[2],m[1]) ; b[1].moulinable = arrayOf(arrayOf(b[0],b[2]), arrayOf(m[1],c[1]))
        b[2].adj = arrayOf(b[1],b[4],m[2]) ; b[2].moulinable = arrayOf(arrayOf(b[0],b[1]), arrayOf(b[4],b[7]), arrayOf(m[2],c[2]))
        b[3].adj = arrayOf(b[0],b[5],m[3]) ; b[3].moulinable = arrayOf(arrayOf(b[0],b[5]), arrayOf(m[3],c[3]))
        b[4].adj = arrayOf(b[2],b[7],m[4]) ; b[4].moulinable = arrayOf(arrayOf(b[2],b[7]), arrayOf(m[4],c[4]))
        b[5].adj = arrayOf(b[3],b[6],m[5]) ; b[5].moulinable = arrayOf(arrayOf(b[0],b[3]), arrayOf(b[6],b[7]), arrayOf(m[5],c[5]))
        b[6].adj = arrayOf(b[5],b[7],m[6]) ; b[6].moulinable = arrayOf(arrayOf(b[5],b[7]), arrayOf(m[6],c[6]))
        b[7].adj = arrayOf(b[4],b[6],m[7]) ; b[7].moulinable = arrayOf(arrayOf(b[5],b[6]), arrayOf(b[2],b[4]), arrayOf(m[7],c[7]))

        m[0].adj = arrayOf(m[1],m[3],b[0],c[0]) ; m[0].moulinable = arrayOf(arrayOf(m[1],m[2]), arrayOf(m[3],m[5]), arrayOf(b[0],c[0]))
        m[1].adj = arrayOf(m[0],m[2],b[1],c[1]) ; m[1].moulinable = arrayOf(arrayOf(m[0],m[2]), arrayOf(b[1],c[1]))
        m[2].adj = arrayOf(m[1],m[4],b[2],c[2]) ; m[2].moulinable = arrayOf(arrayOf(m[0],m[1]), arrayOf(m[4],m[7]), arrayOf(b[2],c[2]))
        m[3].adj = arrayOf(m[0],m[5],b[3],c[3]) ; m[3].moulinable = arrayOf(arrayOf(m[0],m[5]), arrayOf(b[3],c[3]))
        m[4].adj = arrayOf(m[2],m[7],b[4],c[4]) ; m[4].moulinable = arrayOf(arrayOf(m[2],m[7]), arrayOf(b[4],c[4]))
        m[5].adj = arrayOf(m[3],m[6],b[5],c[5]) ; m[5].moulinable = arrayOf(arrayOf(m[0],m[3]), arrayOf(m[6],m[7]), arrayOf(b[5],c[5]))
        m[6].adj = arrayOf(m[5],m[7],b[6],c[6]) ; m[6].moulinable = arrayOf(arrayOf(m[5],m[7]), arrayOf(b[6],c[6]))
        m[7].adj = arrayOf(m[4],m[6],b[7],c[7]) ; m[7].moulinable = arrayOf(arrayOf(m[5],m[6]), arrayOf(m[2],m[4]), arrayOf(b[7],c[7]))

        c[0].adj = arrayOf(c[1],c[3],m[0]) ; c[0].moulinable = arrayOf(arrayOf(c[1],c[2]), arrayOf(c[3],c[5]), arrayOf(b[0],m[0]))
        c[1].adj = arrayOf(c[0],c[2],m[1]) ; c[1].moulinable = arrayOf(arrayOf(c[0],c[2]), arrayOf(b[1],m[1]))
        c[2].adj = arrayOf(c[1],c[4],m[2]) ; c[2].moulinable = arrayOf(arrayOf(c[0],c[1]), arrayOf(c[4],c[7]), arrayOf(b[2],m[2]))
        c[3].adj = arrayOf(c[0],c[5],m[3]) ; c[3].moulinable = arrayOf(arrayOf(c[0],c[5]), arrayOf(b[3],m[3]))
        c[4].adj = arrayOf(c[2],c[7],m[4]) ; c[4].moulinable = arrayOf(arrayOf(c[2],c[7]), arrayOf(b[4],m[4]))
        c[5].adj = arrayOf(c[3],c[6],m[5]) ; c[5].moulinable = arrayOf(arrayOf(c[0],c[3]), arrayOf(c[6],c[7]), arrayOf(b[5],m[5]))
        c[6].adj = arrayOf(c[5],c[7],m[6]) ; c[6].moulinable = arrayOf(arrayOf(c[5],c[7]), arrayOf(b[6],m[6]))
        c[7].adj = arrayOf(c[4],c[6],m[7]) ; c[7].moulinable = arrayOf(arrayOf(c[5],c[6]), arrayOf(c[2],c[4]), arrayOf(b[7],m[7]))
    }

    fun victoire():Boolean{
        return false
    }

    fun placerJeton(joueur: Joueur, position:String):Boolean{
        val pos = position.last().toInt() - 49
        when (position) {
            in "b1".."b8"->
                if (b[pos].jeton == 'o') {
                b[pos].jeton = joueur.nom.first()
                checkMoulin(position, joueur)
            } else return false

            in "m1".."m8" ->
                if(m[pos].jeton == 'o') {
                m[pos].jeton = joueur.nom.first()
                checkMoulin(position,joueur)
            } else return false

            in "c1".."c8" ->
                if(c[pos].jeton == 'o') {
                c[pos].jeton = joueur.nom.first()
                checkMoulin(position,joueur)
            } else return false

            else -> return false
        }

        return true
    }

    fun choixJeton(position: String, joueur: Joueur):Boolean{
        val pos = position.last().code - 49
        return when (position) {
            in "b1".."b8"-> b[pos].jeton == joueur.nom.first()
            in "m1".."m8" -> m[pos].jeton == joueur.nom.first()
            in "c1".."c8" -> c[pos].jeton == joueur.nom.first()
            else -> false
        }
    }

    private fun checkMoulin(position:String, joueur: Joueur){
        val j = joueur.nom.first()
        val p = position.last().code -49
        when(position.first()){
            'b' -> b[p].moulinable.forEach { if ( it[0].jeton == j && it[1].jeton == j) {moulin(b[p],it[0],it[1], joueur.adversaire)} }

            'm' -> m[p].moulinable.forEach { if (it[0].jeton == j && it[1].jeton == j) {moulin(m[p],it[0],it[1], joueur.adversaire)} }

            'c' -> c[p].moulinable.forEach { if (it[0].jeton == j && it[1].jeton == j) {moulin(c[p],it[0],it[1], joueur.adversaire)} }
        }
    }

    private fun moulin(p1:Case,p2:Case,p3:Case,ennemi: String){
        p1.protect = true
        p2.protect = true
        p3.protect = true
        println("Choisissez un jeton ennemi à prendre:")
        while(!enleverJeton(readLine()!!,ennemi)){
            println("Choisissez un jeton ennemi à prendre:")
        }
    }

    private fun enleverJeton(endroit: String, ennemi: String):Boolean {
        val p = endroit.last().code - 49
        when (endroit) {
            in "b1".."b8"-> if(b[p].jeton == ennemi.first() && !b[p].protect) {
                b[p].jeton = 'o'
            } else return false
            in "m1".."m8" -> if(m[p].jeton == ennemi.first() && !m[p].protect) {
                m[p].jeton = 'o'
            } else return false
            in "c1".."c8" -> if(c[p].jeton == ennemi.first() && !c[p].protect) {
                c[p].jeton = 'o'
            } else return false
            else -> return false
        }
        return true
    }

    fun display() {
        val tableau:List<String> = listOf(
                " "+b[0]+"-----"+b[1]+"-----"+b[2]+" ",
                " |\\    |    /| ",
                " | "+m[0]+"---"+m[1]+"---"+m[2]+" | ",
                " | |\\     /| | ",
                " | | "+c[0]+"-"+c[1]+"-"+c[2]+" | | ",
                " | | |   | | | ",
                " "+b[3]+"-"+m[3]+"-"+c[3]+"   "+c[4]+"-"+m[4]+"-"+b[4]+" ",
                " | | |   | | | ",
                " | | "+c[5]+"-"+c[6]+"-"+c[7]+" | | ",
                " | |/     \\| | ",
                " | "+m[5]+"---"+m[6]+"---"+m[7]+" | ",
                " |/    |    \\| ",
                " "+b[5]+"-----"+b[6]+"-----"+b[7]+" "
                )
        for (element in tableau){
            println(element)
        }
    }
}
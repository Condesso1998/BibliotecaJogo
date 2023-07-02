package pt.ipg.bibliotecajogos

data class Jogos(
    var titulo: String,
    var idCategoria: Int,
    var id: Long = -1
) {
}
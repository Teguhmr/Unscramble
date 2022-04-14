/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.unscramble.ui.game


const val MAX_NO_OF_WORDS = 10
const val MAX_NO_OF_HINTS = 3
const val HINT_DECREASE = 1
const val SCORE_INCREASE = 20
var PRESS_COUNTER = 0

data class QnA(
    val question: String,
    val answer: String,
    val arrange: Array<String>
)
// List with all the words for the Game
val allWordsList: List<QnA> =
    listOf(
        QnA(
            "Mata uang Indonesia",
            "RUPIAH",
            arrayOf("P", "U", "P", "R", "N", "A", "I", "U", "H", "L")
        ),
        QnA(
            "Bahasa pemrograman dari Indonesia",
            "JAVA",
            arrayOf("J", "U", "J", "R", "N", "A", "K", "V", "E", "A")
        ),
        QnA(
            "Alat untuk mencatat dan menampilkan gelombang listrik",
            "OSILOSKOP",
            arrayOf("O", "I", "S", "O", "I", "O", "K", "S", "P", "L")
        ),
        QnA(
            "Tepung ubi kayu dinamakan",
            "TAPIOKA",
            arrayOf("A", "P", "I", "T", "N", "A", "K", "U", "E", "O")
        ),
        QnA(
            "Kota Khatulistiwa di Indonesia",
            "PONTIANAK",
            arrayOf("P", "A", "N", "A", "O", "T", "K", "I", "N", "P")
        ),
        QnA(
            "Jumlah bulu ekor Garuda Pancasila",
            "DELAPAN",
            arrayOf("L", "A", "P", "E", "N", "A", "K", "U", "D", "L")
        ),
        QnA(
            "Palung terdalam di dunia",
            "MARIANA",
            arrayOf("M", "U", "J", "R", "N", "A", "I", "U", "A", "A")
        ),
        QnA(
            "Kumpulan kata yang terdiri dari subjek dan predikat",
            "KLAUSA",
            arrayOf("K", "U", "S", "A", "L", "A", "K", "U", "E", "L")
        ),
        QnA(
            "Organisme yang hidup di tempat berkadar garam tinggi",
            "HALOFILI",
            arrayOf("L", "U", "J", "I", "H", "A", "F", "I", "L", "O")
        ),
        QnA(
            "Alat kelamin betina pada bunga",
            "PUTIK",
            arrayOf("T", "U", "P", "B", "N", "A", "K", "I", "G", "S")
        ),
        QnA(
            "Terdapat pada langit-langit gua, ujung meruncing ke bawah",
            "STALAKTIT",
            arrayOf("S", "T", "L", "A", "T", "A", "K", "I", "E", "T")
        ),
        QnA(
            "Tempat penampungan untuk mencegah penyebaran penularan virus dan penyakit",
            "KARANTINA",
            arrayOf("T", "I", "A", "R", "N", "A", "K", "N", "A", "A")
        ),
        QnA(
            "Nama kantor berita nasional di Indonesia",
            "ANTARA",
            arrayOf("A", "U", "T", "R", "N", "A", "K", "U", "A", "N")
        ),
        QnA(
            "Pulau di Indonesia yang berbatasan dengan Malaysia",
            "KALIMANTAN",
            arrayOf("K", "A", "N", "T", "N", "A", "A", "M", "I", "L")
        ),
        QnA(
            "Telur yang diaduk bersama bumbu lalu digoreng",
            "DADAR",
            arrayOf("C", "A", "D", "A", "R", "K", "O", "P", "D", "L")
        ),
        QnA(
            "Orang yang sedang menjalani masa hukuman pidana",
            "NARAPIDANA",
            arrayOf("P", "A", "D", "A", "R", "A", "A", "N", "I", "N")
        ),
        QnA(
            "Candi Budha terbesar di dunia",
            "BOROBUDUR",
            arrayOf("B", "U", "D", "O", "R", "B", "U", "R", "I", "O")
        ),
        QnA(
            "Jangka waktu yang lamanya 100 tahun",
            "ABAD",
            arrayOf("E", "D", "D", "K", "R", "B", "A", "E", "B", "A")
        ),
        QnA(
            "Ibukota provinsi Papua Barat",
            "MANOKWARI",
            arrayOf("M", "O", "N", "K", "A", "W", "A", "R", "I", "A")
        ),
        QnA(
            "Penyakit kurang darah dinamakan",
            "ANEMIA",
            arrayOf("A", "E", "N", "I", "A", "M", "A", "R", "I", "A")
        ),
        QnA(
            "Tepung yang terbuat dari jagung dinamakan",
            "MAIZENA",
            arrayOf("A", "E", "N", "I", "A", "E", "A", "Z", "I", "M")
        ),
        QnA(
            "Komunitas hewan, tumbuhan dan habitatnya",
            "EKOSISTEM",
            arrayOf("K", "E", "O", "I", "S", "S", "E", "T", "I", "M")
        ),
        QnA(
            "Toko tempat penjualan dan peramuan obat",
            "APOTEK",
            arrayOf("P", "E", "O", "A", "S", "K", "E", "T", "I", "M")
        ),
        QnA(
            "Ilmu tentang interaksi organisme & lingkungan",
            "EKOLOGI",
            arrayOf("E", "K", "O", "A", "S", "L", "E", "G", "I", "O")
        ),
    )

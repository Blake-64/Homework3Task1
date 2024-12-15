package com.example.blakecornishhomework3task1

class BitCoinRepositoryImpl: BitCoinRepository {
    override fun getBitCoins(): List<BitCoin> {
        return listOf(
            BitCoin(name = "andrew", symbol = "Bold", id = "one", priceUsd = 4.283),
            BitCoin(name = "bob", symbol = "Bold", id = "two", priceUsd = 6.843),
            BitCoin(name = "connie", symbol = "Bold", id = "three", priceUsd = 1.293),
            BitCoin(name = "daniel", symbol = "Bold", id = "four", priceUsd = 0.83)
        )
    }
}
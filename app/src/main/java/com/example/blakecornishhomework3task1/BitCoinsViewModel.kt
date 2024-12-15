package com.example.blakecornishhomework3task1

import androidx.lifecycle.ViewModel

class BitCoinsViewModel(
    private val bitcoinsRepository: BitCoinRepository
): ViewModel() {
    fun getBitCoins() = bitcoinsRepository.getBitCoins()
}
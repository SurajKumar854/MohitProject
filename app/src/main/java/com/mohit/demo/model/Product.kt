package com.mohit.demo.model

import java.util.UUID

data class Product(val id: String, val name: String, var isSelected: Boolean = false)
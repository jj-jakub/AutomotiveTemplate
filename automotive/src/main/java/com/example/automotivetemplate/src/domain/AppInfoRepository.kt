package com.example.automotivetemplate.src.domain

interface AppInfoRepository {
    fun getRequiredPermissions(): List<String>
}
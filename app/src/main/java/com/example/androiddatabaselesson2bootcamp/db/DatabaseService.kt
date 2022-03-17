package com.example.androiddatabaselesson2bootcamp.db

import com.example.androiddatabaselesson2bootcamp.models.BSWType
import com.example.androiddatabaselesson2bootcamp.models.Basic
import com.example.androiddatabaselesson2bootcamp.models.Social
import com.example.androiddatabaselesson2bootcamp.models.World

interface DatabaseService {

    fun insertBasic(basic: BSWType)

    fun insertWorld(world: BSWType)

    fun insertSocial(social: BSWType)

    fun getBasicById(id: Int): BSWType

    fun getWorldById(id: Int): BSWType

    fun getSocialById(id: Int): BSWType

    fun getAllBasic(): ArrayList<BSWType>

    fun getAllWorld(): ArrayList<BSWType>

    fun getAllSocial(): ArrayList<BSWType>

    fun getBasicCount(): Int

    fun getWorldCount(): Int

    fun getSocialCount(): Int

    fun updateBasic(basic: BSWType): Int

    fun updateWorld(world: BSWType): Int

    fun updateSocial(social: BSWType): Int

    fun deleteBasic(basic: BSWType)

    fun deleteWorld(world: BSWType)

    fun deleteSocial(social: BSWType)

}
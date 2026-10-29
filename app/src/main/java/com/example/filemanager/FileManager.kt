package com.example.filemanager

import java.io.File

class FileManager {

    fun showContents(path: String): String {
        if (path.isBlank()) {
            return "Путь не указан"
        }

        val directory = File(path)

        if (!directory.exists()) {
            return "Каталог не существует"
        }

        if (!directory.isDirectory) {
            return "Указанный путь не является каталогом"
        }

        val files = directory.listFiles()

        if (files == null) {
            return "Не удалось получить содержимое каталога"
        }

        if (files.isEmpty()) {
            return "Каталог пуст"
        }

        return files.joinToString("\n") {
            if (it.isDirectory) {
                "[ПАПКА] ${it.name}"
            } else {
                "[ФАЙЛ] ${it.name}"
            }
        }
    }

    fun createFolder(path: String, folderName: String): Boolean {
        if (path.isBlank() || folderName.isBlank()) {
            return false
        }

        val directory = File(path)

        if (!directory.exists() || !directory.isDirectory) {
            return false
        }

        val newFolder = File(directory, folderName)

        return newFolder.mkdir()
    }

    fun deleteFolder(path: String, folderName: String): Boolean {
        if (path.isBlank() || folderName.isBlank()) {
            return false
        }

        val folder = File(path, folderName)

        if (!folder.exists() || !folder.isDirectory) {
            return false
        }

        if (folder.listFiles()?.isNotEmpty() == true) {
            return false
        }

        return folder.delete()
    }
}
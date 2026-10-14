package com.example.filemanager

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class FileManagerTest {

    @Test
    fun showContents_emptyPath_returnsError() {
        val manager = FileManager()

        val result = manager.showContents("")

        assertEquals("Путь не указан", result)
    }

    @Test
    fun showContents_nonExistingDirectory_returnsError() {
        val manager = FileManager()

        val result = manager.showContents(
            "/this/directory/does/not/exist"
        )

        assertEquals("Каталог не существует", result)
    }

    @Test
    fun showContents_fileInsteadOfDirectory_returnsError() {
        val manager = FileManager()

        val file = File.createTempFile("test", ".txt")

        try {
            val result = manager.showContents(file.absolutePath)

            assertEquals(
                "Указанный путь не является каталогом",
                result
            )
        } finally {
            file.delete()
        }
    }

    @Test
    fun createFolder_emptyPath_returnsFalse() {
        val manager = FileManager()

        val result = manager.createFolder(
            "",
            "TestFolder"
        )

        assertFalse(result)
    }

    @Test
    fun createFolder_emptyName_returnsFalse() {
        val manager = FileManager()

        val directory = createTempDir()

        try {
            val result = manager.createFolder(
                directory.absolutePath,
                ""
            )

            assertFalse(result)
        } finally {
            directory.delete()
        }
    }

    @Test
    fun createFolder_validData_createsFolder() {
        val manager = FileManager()

        val directory = createTempDir()

        try {
            val result = manager.createFolder(
                directory.absolutePath,
                "TestFolder"
            )

            assertTrue(result)
            assertTrue(
                File(directory, "TestFolder").exists()
            )
        } finally {
            File(directory, "TestFolder").delete()
            directory.delete()
        }
    }

    @Test
    fun deleteFolder_nonExistingFolder_returnsFalse() {
        val manager = FileManager()

        val directory = createTempDir()

        try {
            val result = manager.deleteFolder(
                directory.absolutePath,
                "UnknownFolder"
            )

            assertFalse(result)
        } finally {
            directory.delete()
        }
    }

    @Test
    fun deleteFolder_existingEmptyFolder_returnsTrue() {
        val manager = FileManager()

        val directory = createTempDir()
        val folder = File(directory, "TestFolder")

        folder.mkdir()

        try {
            val result = manager.deleteFolder(
                directory.absolutePath,
                "TestFolder"
            )

            assertTrue(result)
            assertFalse(folder.exists())
        } finally {
            folder.delete()
            directory.delete()
        }
    }
}
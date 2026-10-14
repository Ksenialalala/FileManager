package com.example.filemanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etPath: EditText
    private lateinit var etFolderName: EditText
    private lateinit var tvContents: TextView

    private lateinit var rbShow: RadioButton
    private lateinit var rbCreate: RadioButton
    private lateinit var rbDelete: RadioButton

    private lateinit var btnExecute: Button

    private val fileManager = FileManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        etPath = findViewById(R.id.etPath)
        etPath.setText(filesDir.absolutePath)
        etFolderName = findViewById(R.id.etFolderName)
        tvContents = findViewById(R.id.tvContents)

        rbShow = findViewById(R.id.rbShow)
        rbCreate = findViewById(R.id.rbCreate)
        rbDelete = findViewById(R.id.rbDelete)

        btnExecute = findViewById(R.id.btnExecute)

        btnExecute.setOnClickListener {
            executeOperation()
        }
    }

    private fun executeOperation() {
        val path = etPath.text.toString().trim()
        val folderName = etFolderName.text.toString().trim()

        when {
            rbShow.isChecked -> {
                tvContents.text = fileManager.showContents(path)
            }

            rbCreate.isChecked -> {
                val result = fileManager.createFolder(
                    path,
                    folderName
                )

                val message = if (result) {
                    "Папка успешно создана"
                } else {
                    "Не удалось создать папку"
                }

                Toast.makeText(
                    this,
                    message,
                    Toast.LENGTH_LONG
                ).show()

                if (result) {
                    tvContents.text = fileManager.showContents(path)
                }
            }

            rbDelete.isChecked -> {
                val result = fileManager.deleteFolder(
                    path,
                    folderName
                )

                val message = if (result) {
                    "Папка успешно удалена"
                } else {
                    "Не удалось удалить папку"
                }

                Toast.makeText(
                    this,
                    message,
                    Toast.LENGTH_LONG
                ).show()

                if (result) {
                    tvContents.text = fileManager.showContents(path)
                }
            }
        }
    }
}

package com.example.challengeroom1anggita_eka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeroom1anggita_eka.room.Dbsmksa
import com.example.challengeroom1anggita_eka.room.constant
import com.example.challengeroom1anggita_eka.room.tbsiswa
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy { Dbsmksa(this) }
    lateinit var siswaAdapter: SiswaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        halEdit()
        setupRecyclerview()

    }

    override fun onStart() {
        super.onStart()
        loadtbSiswa()
    }
    fun loadtbSiswa(){
        CoroutineScope(Dispatchers.IO).launch {
            val siswa = db.tbsisDao().tampilsemua()
            Log.d("MainActivity", "dbRespons: $siswa")
            withContext(Dispatchers.Main) {
                siswaAdapter.setData(siswa)
            }
        }
    }
    private fun halEdit() {
        Btinput.setOnClickListener {
            intentEdit(0, constant.TYPE_CREATE)
        }
    }

    fun intentEdit(tbSisnis: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, EditActivity::class.java)
                .putExtra("Intent_nis", tbSisnis)
                .putExtra("intent_type", intentType)
        )
    }

   private fun setupRecyclerview(){
        siswaAdapter = SiswaAdapter(arrayListOf(), object : SiswaAdapter.OnAdapterListener{
            override fun onClick(tbSis: tbsiswa) {
                    intentEdit(tbSis.nis, constant.TYPE_READ)
                //toast
                Toast.makeText(applicationContext, tbSis.nama, Toast.LENGTH_SHORT).show()
            }

            override fun onUpdate(tbSis: tbsiswa) {
                intentEdit(tbSis.nis, constant.TYPE_UPDATE)
            }

            override fun onDelete(tbSis: tbsiswa) {
                deleteAlert(tbSis)
            }

        })
        //id recyclerview
        listdatasiswa.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = siswaAdapter
        }
    }
    private fun deleteAlert(tbsis: tbsiswa){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin hapus ${tbsis.nama}?")
            setNegativeButton("Batal",) {dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") {dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbsisDao().deletetbsiswa(tbsis)
                    loadtbSiswa()
                }
            }
        }
        alertDialog.show()
    }
}
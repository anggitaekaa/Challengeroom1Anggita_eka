package com.example.challengeroom1anggita_eka

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.challengeroom1anggita_eka.room.Dbsmksa
import com.example.challengeroom1anggita_eka.room.constant
import com.example.challengeroom1anggita_eka.room.tbsiswa
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EditActivity : AppCompatActivity() {
    val db by lazy { Dbsmksa(this) }
    private var tbSisnis: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        tombolPerintah()
        tbSisnis = intent.getIntExtra("Intent_nis", tbSisnis)
        Toast.makeText(this, tbSisnis.toString(), Toast.LENGTH_SHORT).show()
    }

    fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            constant.TYPE_CREATE -> {
                btupdate.visibility = View.GONE
            }
            constant.TYPE_READ -> {
                btsimpan.visibility = View.GONE
                btupdate.visibility = View.GONE
                etnis.visibility = View.GONE
                tampilsemua()
            }
            constant.TYPE_UPDATE -> {
                btsimpan.visibility = View.GONE
                etnis.visibility = View.GONE
                tampilsemua()
                }
        }
    }

    private fun tombolPerintah() {
        btsimpan.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbsisDao().addtbsiswa(
                    tbsiswa(etnis.text.toString().toInt(), etnama.text.toString(),
                        etkelas.text.toString(), etalamat.text.toString())
                )
                finish()
            }
        }
        btupdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbsisDao().updatetbsiswa(
                    tbsiswa( tbSisnis, etnama.text.toString(),
                        etkelas.text.toString(),etalamat.text.toString())
                )
                finish()
            }
        }
    }



    fun tampilsemua() {
        tbSisnis = intent.getIntExtra("Intent_nis", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val smk = db.tbsisDao().tampilid( tbSisnis)[0]
            //etnis.setText(siswa.nis)
            etnama.setText(smk.nama)
            etkelas.setText(smk.kelas)
            etalamat.setText(smk.alamat)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp() }
}
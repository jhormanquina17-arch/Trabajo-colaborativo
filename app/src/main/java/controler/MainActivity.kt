package controler

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alexrosero.trabajoenequipo.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import model.LoginRequest
import model.RetrofitClient

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Capturamos el clic del botón para mandar los datos que escribas en los EditText
        binding.btnLogin.setOnClickListener {
            val usuario = binding.etUsuario.text.toString().trim()
            val clave = binding.etClave.text.toString().trim()

            if (usuario.isNotEmpty() && clave.isNotEmpty()) {
                hacerLogin(usuario, clave)
            } else {
                binding.tvTokenStatus.text = "Por favor ingresa usuario y contraseña"
            }
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
        }
    }

    // ---------- PASO A: POST de login ----------
    private fun hacerLogin(usuario: String, clave: String) {
        binding.tvTokenStatus.text = "Estado: Conectando..."
        lifecycleScope.launch {
            try {
                val resp = RetrofitClient.api.login(
                    LoginRequest(usuario, clave)
                )
                if (resp.isSuccessful) {
                    token = resp.body()?.accessToken
                    Log.d("API", "Token recibido: $token")

                    binding.tvTokenStatus.text = "Estado: ¡Login exitoso!"
                    obtenerUsuario() // seguimos al GET
                } else {
                    Log.e("API", "Login falló: ${resp.code()}")
                    binding.tvTokenStatus.text = "Estado: Credenciales incorrectas (${resp.code()})"
                }
            } catch (e: Exception) {
                Log.e("API", "Error de red: ${e.message}")
                binding.tvTokenStatus.text = "Estado: Error de red"
            }
        }
    }

    // ---------- PASO B: GET protegido con el token ----------
    private fun obtenerUsuario() {
        val t = token ?: return
        lifecycleScope.launch {
            try {
                val resp = RetrofitClient.api.getCurrentUser("Bearer $t")
                if (resp.isSuccessful) {
                    val user = resp.body()
                    Log.d("API", "Hola ${user?.firstName} - ${user?.email}")

                    binding.tvNombreUsuario.text = "Nombre: ${user?.firstName ?: "Sin nombre"}"
                    binding.tvCorreoUsuario.text = "Correo: ${user?.email ?: "Sin correo"}"
                } else {
                    Log.e("API", "Error al obtener usuario: ${resp.code()}")
                }
            } catch (e: Exception) {
                Log.e("API", "Error: ${e.message}")
            }
        }
    }
}
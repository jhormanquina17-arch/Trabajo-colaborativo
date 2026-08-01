package controler

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alexrosero.trabajoenequipo.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import model.LoginRequest
import model.RetrofitClient

class MainActivity : AppCompatActivity() {

    // Declaramos el binding
    private lateinit var binding: ActivityMainBinding
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializamos el View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ejecutamos el login al iniciar la app
        hacerLogin("emilys", "emilyspass")
    }

    // ---------- PASO A: POST de login ----------
    private fun hacerLogin(usuario: String, clave: String) {
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
                    binding.tvTokenStatus.text = "Estado: Falló el login (${resp.code()})"
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

                    // Mostramos los datos en la pantalla usando binding
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
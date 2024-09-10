import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistroViewModel : ViewModel() {
    private val _registroExitoso = MutableLiveData<Boolean>()
    val registroExitoso: LiveData<Boolean> get() = _registroExitoso

    fun registrarUsuario(
        nombre: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        telefono: String,
        direccion: String,
        email: String,
        password: String
    ) {
        val usuarioValido = nombre.isNotEmpty() && apellidoPaterno.isNotEmpty() && apellidoMaterno.isNotEmpty() &&
                telefono.isNotEmpty() && direccion.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()

        _registroExitoso.value = usuarioValido
    }
}

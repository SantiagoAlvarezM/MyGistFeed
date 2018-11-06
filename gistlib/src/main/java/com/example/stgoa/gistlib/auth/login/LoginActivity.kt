package com.example.stgoa.gistlib.auth.login

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.stgoa.gistlib.R
import com.example.stgoa.gistlib.auth.login.model.LoginUIModel
import com.example.stgoa.gistlib.auth.view.TextWatcherAdapter
import com.example.stgoa.gistlib.common.disposeSecure
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: LoginViewModel
    private var manageViewDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initListeners()
        startObserving()
    }

    private fun startObserving() {
        manageViewDisposable = viewModel.observableManageView()
            .subscribe({ uiModel ->
                when (uiModel) {
                    is LoginUIModel.ShowProgress -> showProgress(uiModel.show)
                    is LoginUIModel.LoginFails -> onLoginFails()
                    LoginUIModel.LoginSuccess -> onLoginSuccess()
                }
            }, { showError() })
    }

    private fun onLoginSuccess() {
        finish()
    }

    private fun onLoginFails() {
        //TODO map errors
        showError()
        resetViews()
    }

    private fun initListeners() {
        buttonSignIn.setOnClickListener {
            if (canContinueLogin()) {
                showProgress(true)
                viewModel.doBasicLogin(editTextUsername.text.toString(), editTextPassword.text.toString())
            }
        }

        editTextUsername.addTextChangedListener(object : TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                setErrorForInput(editTextUsername)
            }
        })

        editTextPassword.addTextChangedListener(object : TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                setErrorForInput(editTextPassword)
            }
        })
    }

    private fun showError(message: String = getString(R.string.general_error)) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgress(show: Boolean) {
        buttonSignIn.isEnabled = !show
        loginProgress.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun resetViews() {
        editTextPassword.text?.clear()
        buttonSignIn.isEnabled = true
        arrayOf(editTextUsername, editTextPassword).map { setErrorForInput(it) }
    }

    private fun canContinueLogin(): Boolean {
        return evaluateEditText(editTextUsername) and evaluateEditText(editTextPassword)
    }

    private fun evaluateEditText(editText: EditText): Boolean {
        if (editText.text.isEmpty()) {
            setErrorForInput(editText, getString(R.string.error_field_required))
            return false
        }
        return true
    }

    private fun setErrorForInput(editText: EditText, message: String? = null) {
        when (editText.id) {
            R.id.editTextUsername -> textInputUsername.error = message
            R.id.editTextPassword -> textInputPassword.error = message
        }
    }

    override fun onDestroy() {
        viewModel.disposable.disposeSecure()
        manageViewDisposable?.disposeSecure()
        super.onDestroy()

    }
}

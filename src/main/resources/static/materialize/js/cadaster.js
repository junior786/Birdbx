var botao = document.getElementById('cadastrar')
var senha = document.getElementById('password')
var repetida = document.getElementById('passwordAgain')
var verificacao = document.getElementById('verificacao')
botao.addEventListener('click',verificarSenha)

function verificarSenha(){
    if(senhaVerificar(senha.value)){
        if(senha.value == repetida.value){
            document.forms['cadastro'].submit()
        }else{
            verificacao.innerText = 'As senhas nao são iguais'
            repetida.style.border = '2px solid #FF0000'
        }
    }else{
        verificacao.innerText = 'A senha deve conter pelo menos 4 caracteres'
    }
}

function senhaVerificar(senha){
    if (String(senha).length > 3){
        return true
    }else{
        return false
    }

}
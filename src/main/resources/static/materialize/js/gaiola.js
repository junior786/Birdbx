var numeroGaiola = document.getElementById('nrGaiola')
var botaoEnviar = document.getElementById('enviar')
var erro = document.getElementById('erro')
botaoEnviar.addEventListener('click', errors)

function errors(){
    var verificar = true;
    var gaiolas = document.querySelectorAll('#numeroFilhote');

    for(var i = 0; i < gaiolas.length; i++){
        if(numeroGaiola.value == Number(gaiolas[i].textContent)){
            erro.innerText = "Já contem uma gaiola com esse número, por favor verificar"
            numeroGaiola.style.border = "3px solid #FF0000"
            verificar = false;
    }
        }
        if(verificar && numeroGaiola.value != 0){
             document.forms['theForm'].submit()
        }



}
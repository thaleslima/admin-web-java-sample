function apagarUsuario(texto)
{
	document.getElementById('sUser').innerHTML = "";
	document.getElementById('sNome').innerHTML = "";
	document.getElementById('sSenha').innerHTML = "";
	document.getElementById('sEmail').innerHTML = "";
	
	document.getElementById('tituloCadastro').innerHTML = texto;
	document.getElementById('insertUpdate').value = "Insert";
	document.getElementById('txtIdUser').value = "";
	document.getElementById('txtUser').value = "";
	document.getElementById('txtNome').value = "";
	document.getElementById('txtSenha').value = "";
	document.getElementById('txtEmail').value = "";
	document.getElementById('txtStatus').value = 1;
	document.getElementById('txtFlProibaltSenha').value = "S";
	document.getElementById('txtIdioma').value = 1;
}



function validarUsuario() {

	document.getElementById('sUser').innerHTML = "";
	document.getElementById('sNome').innerHTML = "";
	document.getElementById('sEmail').innerHTML = "";

	user = document.getElementById('tvEntidade:txtUser').value;
	nome = document.getElementById('tvEntidade:txtNome').value;
	senha = document.getElementById('tvEntidade:txtSenha').value;
	email = document.getElementById('tvEntidade:txtEmail').value;
	
	saida = true;

	if( user == '' )
	{
		document.getElementById('sUser').innerHTML = "Valor Obrigatório";
		saida = false;
	}

	
	if( nome == '' )
	{
		document.getElementById('sNome').innerHTML = "Valor Obrigatório";
		saida = false;
	}


	if( email == '' )
	{
		document.getElementById('sEmail').innerHTML = "Valor Obrigatório";
		saida = false;
	}


	return saida;
}


function validarGrupo() {

	document.getElementById('sNome').innerHTML = "";

	nome = document.getElementById('tvEntidade:txtNome').value;

	saida = true;

	if( nome == '' )
	{
		document.getElementById('sNome').innerHTML = "Valor Obrigatório";
		saida = false;
	}

	return saida;
}

function validarConfUsuario() {


	
	document.getElementById('sconfNome').innerHTML = "";
	document.getElementById('sconfEmail').innerHTML = "";

	
	nome = document.getElementById('txtconftNome').value;
	email = document.getElementById('txtconfEmail').value;
	
	saida = true;

	
	if( nome == '' )
	{
		document.getElementById('sconfNome').innerHTML = "Valor Obrigatório";
		saida = false;
	}

	
	if( email == '' )
	{
		document.getElementById('sconfEmail').innerHTML = "Valor Obrigatório";
		saida = false;
	}

	
	
	return saida;
}

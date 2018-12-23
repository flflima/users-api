# users-api
Users creation API

####Endpoints:

######POST - Criar usuários

	http://localhost:8080/users
	
	https://users-api-cs.herokuapp.com/users

#####POST - Login usuários

	http://localhost:8080/users/login
	
	https://users-api-cs.herokuapp.com/users/login

#####POST - Perfil usuários

	http://localhost:8080/users/login/{id}
	
	https://users-api-cs.herokuapp.com/users/login/{id}

	<b>onde {id} é o id do usuário</b>
	
	<b>* necessário enviar token de autorização no HEADER da requisição: -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiMjE5ZjlmOC0yYWNiLTQzMTItYTJhYS00ZTEwMzhlYzQ0NTEifQ.l25tcNiCZqetjKuQZlPKFhhw7QaLD_THEgI1d6hcsUBplmmoGmweyWK_EapyqTuFzICA2SBN39bcTZV9qABbdQ'</b>
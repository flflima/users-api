# users-api
Users creation API

#### Para executar localmente

	A partir do diretório raiz:
	
	gradle bootRun

#### Endpoints:

##### POST - Criar usuários

	http://localhost:8080/users
	
	https://users-api-cs.herokuapp.com/users
	
	{
        "name": "João da Silva",
        "email": "joao@silva.org",
        "password": "hunter2",
        "phones": [
            {
                "number": "987654321",
                "ddd": "21"
            }
        ]
    }

##### POST - Login usuários

	http://localhost:8080/users/login
	
	https://users-api-cs.herokuapp.com/users/login
	
	{
        "email": "joao@silva.org",
        "password": "hunter2"
    }

##### POST - Perfil usuários

	http://localhost:8080/users/login/{id}
	
	https://users-api-cs.herokuapp.com/users/login/{id}

- onde **{id}** é o id do usuário
	
- necessário enviar token de autorização no HEADER da requisição: **-H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiMjE5ZjlmOC0yYWNiLTQzMTItYTJhYS00ZTEwMzhlYzQ0NTEifQ.l25tcNiCZqetjKuQZlPKFhhw7QaLD_THEgI1d6hcsUBplmmoGmweyWK_EapyqTuFzICA2SBN39bcTZV9qABbdQ'**

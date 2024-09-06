package com.vosouza.appfilmes.data.exception

class InternetException : Exception("Você está sem internet")

class Http400Exception : Exception("Estamos com problemas ao tentar acessar os servidores")

class NullBodyException : Exception("Estamos com problemas ao tentar acessar os servidores")

class GenericException : Exception("Estamos com problemas, tente novamente mais tarde")
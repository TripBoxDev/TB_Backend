
/**
 * Este package contiene todas las excepciones HTTP que puede retornar la API.
 * 
 * ElementNotFoundException: Excepcion que se utiliza cuando no se ha encontrdo un elemento (lo mas probable, con una ID) en la base de datos (un usuario, un grupo, una card...)
 * MethodNotImplementedException: Excepcion que se utiliza cuando se intenta acceder a un metodo que no esta implementado en la API.
 * RequiredParamsFail: Excepcion que se utiliza cuando uno de los parametros obligatorios es incorrecto. Por ejemplo si una card no tuviera tipo o no fuera de los tipos permitidos por la aplicacion.
 * UserNotExistOnGroupREST: Excepcion que se utiliza cuando un usuario especificado en la llamada no forma parte del grupo especificado tambien en la llamada.
 * 
 * @author santi
 *
 */
package com.tripbox.api.exceptions;
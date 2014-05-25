
/**
 * Este package contiene todas las excepciones que pueden surgir en el service.
 * 
 * CardTypeException: Excepcion que se utiliza cuando el tipo de una card es incorrecto.
 * DestinationAlreadyExistsException: Excepcion que se utiliza cuando se intenta anadir un destino que ya existe.
 * ElementNotFoundServiceException: Excepcion que se utiliza cuando no se ha encontrdo un elemento con ID en la base de datos (un usuario, un grupo, una card...)
 * IdAlreadyExistException: Excepcion que se utiliza cuando se intenta generar una ID que ya existe.
 * InvalidIdsException: Excepcion utilizada para indicar que el elemento que ha recibido el servidor no tiene ningun identificador definido o valido.
 * RequiredParametersException: Excepcion que se utiliza cuando uno de los parametros obligatorios es incorrecto.
 * UserNotExistOnGroupREST: Excepcion que se utiliza cuando un usuario especificado en la llamada no forma parte del grupo especificado tambien en la llamada.
 * 
 * @author santi
 *
 */
package com.tripbox.services.exceptions;
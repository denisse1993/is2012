package com.cinnamon.is.comun;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

/**
 * Clase que contiene los metodos para interaccionar con la BD
 * 
 * @author Cinnamon Team
 * 
 */
public class Conexion {

	/**
	 * Guarda el contexto de la actividad que le llama
	 */
	public Activity activity;

	/**
	 * Guarda la respuesta del servidor, puede ser valores de control o
	 * informacion para parsear
	 */
	private String respuesta;

	/**
	 * Constructor
	 * 
	 * @param _activity
	 * 
	 * @param _context
	 */
	public Conexion(final Activity _activity) {
		this.activity = _activity;
		this.respuesta = "";
	}

	/**
	 * Getters
	 * 
	 * @return String
	 */

	public String getRespuesta() {
		return this.respuesta;
	}

	/**
	 * Login de un usuario
	 * 
	 * @param nick
	 *            Nombre de usuario
	 * @param pass
	 *            Password
	 * @return True exito false si no
	 */
	public boolean login(final String nick, final String pass) {
		boolean retorno;
		HttpClient hc = new DefaultHttpClient();
		// HttpPost post = new HttpPost("http://10.0.2.2/login.php"); //local
		HttpPost post = new HttpPost("http://cinnamon.webatu.com/login.php"); // server
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("user", nick));
		pairs.add(new BasicNameValuePair("pass", pass));

		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				this.respuesta = EntityUtils.toString(rp.getEntity());
				retorno = true;
			} else {
				retorno = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("ERROR", "CONECTION PROBLEM");
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Registra un usuario en la BD online
	 * 
	 * @param nick
	 *            Nombre de usuario
	 * @param pass
	 *            password del usuario
	 * @return True exito, false si hubo algun problema
	 */
	public boolean register(final String nick, final String pass) {
		HttpClient hc = new DefaultHttpClient();
		boolean retorno;
		// HttpPost post = new HttpPost("http://10.0.2.2/register.php");
		HttpPost post = new HttpPost("http://cinnamon.webatu.com/register.php"); // server
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("user", nick));
		pairs.add(new BasicNameValuePair("pass", pass));
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				this.respuesta = EntityUtils.toString(rp.getEntity());
				if (this.respuesta.length() > 1) {
					this.respuesta = this.respuesta.substring(0, 1);
				}
				retorno = true;
			} else {
				retorno = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Consigue el ranking del servidor y lo guarda en respuesta
	 * 
	 * @return true o false si fue exitoso o no
	 */
	public boolean dameOnlineArcade() {

		HttpClient hc = new DefaultHttpClient();
		boolean retorno;
		// HttpPost post = new HttpPost("http://10.0.2.2/arcade.php");
		HttpPost post = new HttpPost("http://cinnamon.webatu.com/arcade.php"); // server
		try {
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				this.respuesta = EntityUtils.toString(rp.getEntity());
				retorno = true;
			} else {
				retorno = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Codifica md5
	 * 
	 * @param clear
	 * @return Codificado a Md5
	 * @throws NoSuchAlgorithmException
	 */
	public static String toMD5(final String clear)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] b = md.digest(clear.getBytes());

		int size = b.length;
		StringBuffer h = new StringBuffer(size);
		for (int i = 0; i < size; i++) {
			int u = b[i] & 255;
			if (u < 16) {
				h.append("0" + Integer.toHexString(u));
			} else {
				h.append(Integer.toHexString(u));
			}
		}
		return h.toString();
	}

	/**
	 * 
	 * @param idMJ
	 *            ID del minijuego
	 * @param nick
	 *            Nombre Usuario
	 * @param score
	 *            Puntuacion del minijuego
	 * @return True exito false sino
	 */
	public boolean updateScore(final int idMJ, final String nick,
			final String score) {
		HttpClient hc = new DefaultHttpClient();
		boolean retorno;
		// HttpPost post = new HttpPost("http://10.0.2.2/register.php");
		HttpPost post = new HttpPost("http://cinnamon.webatu.com/updateMJ.php"); // server
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("mj", idMJ + ""));
		pairs.add(new BasicNameValuePair("user", nick));
		pairs.add(new BasicNameValuePair("puntuacion", score));
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				this.respuesta = EntityUtils.toString(rp.getEntity());
				retorno = true;
			} else {
				retorno = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Sube una imagen al servidor
	 * 
	 * @param imagen
	 *            Imagen a subir
	 * @return True exito
	 */
	public boolean uploadImage(final int imagen) throws IOException {
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		Bitmap uploadIMG = BitmapFactory.decodeResource(
				this.activity.getResources(), imagen);
		;
		uploadIMG.compress(Bitmap.CompressFormat.JPEG, 90, bao);
		String name = "launcher";
		byte[] ba = bao.toByteArray();
		String encodedImage = Base64.encodeToString(ba, Base64.DEFAULT);
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("nombre", name));
		pairs.add(new BasicNameValuePair("image", encodedImage));
		HttpClient hc = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://10.0.2.2/uploadIMG.php");
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = EntityUtils.toString(rp.getEntity());
				Toast.makeText(this.activity.getApplicationContext(), str,
						Toast.LENGTH_LONG).show();
				return true;
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;

	}

	public Bitmap decodificaBase64(final String encodedImage) {

		byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
				decodedString.length);
		return decodedByte;
	}

	/**
	 * Actualiza la tabla arcade
	 * 
	 * @param arraySc
	 *            array de 12 casillas con los valores
	 * @param nick
	 *            el nombre del jugador
	 * @param token
	 *            token unico de acceso para este usuario
	 * @return true o false en funcion si exito o no
	 */
	public boolean updateArcade(final int[] arraySc, final String nick,
			final String token) {
		// Vuelca toda la info de BD local en la BD web
		HttpClient hc = new DefaultHttpClient();
		boolean retorno;
		// HttpPost post = new HttpPost("http://10.0.2.2/register.php");
		HttpPost post = new HttpPost(
				"http://cinnamon.webatu.com/updateArcade.php"); // server
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		int i = 0;

		while (i < 12) {
			String actual = Integer.toString(arraySc[i]);
			int code = i + 1;
			pairs.add(new BasicNameValuePair("mj" + code, actual));
			i++;
		}
		pairs.add(new BasicNameValuePair("user", nick));
		pairs.add(new BasicNameValuePair("token", token));
		// pairs.add(new BasicNameValuePair("puntuacion", score));
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				this.respuesta = EntityUtils.toString(rp.getEntity());
				retorno = true;
			} else {
				retorno = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Crea una nueva aventura en el servidor
	 * 
	 * longitudes actuales mj = 1, pista = 30; nombreAv = 11, pass da igual xq
	 * la convierto a md5 32chars el post devuelve en respuesta 1 = correcto, 2
	 * = error en el formato de envio 3 = no se pudo abrir la db
	 * 
	 * @param mj
	 *            array de 12 casillas con los minijuegos, si mj1 es usado
	 *            valdra 1, si mj5 es usado valdra 5, etc
	 * @param pista
	 *            array de 12 casillas que contienen las pistas asociadas a cada
	 *            minijuego, en la posicion 7 estara la pista del mj7 (si se
	 *            usa)
	 * @param nombreAventura
	 *            el nombre de la aventura(sera la clave)
	 * @param passAventura
	 *            pass de la aventura para editar en un futuro
	 * @param update
	 *            operacion de actualizacion o no
	 * @param nick
	 *            del usuario
	 * @param token
	 *            unico pa
	 * 
	 * @return boolean exito o no
	 */
	public boolean creaOnlineAventura(final String[] mj, final String[] pista,
			final String nombreAventura, final String passAventura,
			final boolean update, final String nick, final String token) {
		// se deberia tambien comprobar el usuario que crea la aventura, sino el
		// token no tiene sentido
		boolean retorno;
		// Vuelca toda la info de BD local en la BD web
		HttpClient hc = new DefaultHttpClient();
		// HttpPost post = new HttpPost("http://10.0.2.2/register.php");
		HttpPost post;
		if (!update) {
			post = new HttpPost("http://cinnamon.webatu.com/creaAventura.php"); // server
		} else {
			post = new HttpPost("http://cinnamon.webatu.com/updateAventura.php"); // server
		}

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("nombre", nombreAventura));
		pairs.add(new BasicNameValuePair("pass", passAventura));

		int i = 0;
		while (i < 12) {
			int code = i + 1;
			String pi = pista[i];
			String mi = mj[i];
			pairs.add(new BasicNameValuePair("mj" + code, mi));
			pairs.add(new BasicNameValuePair("pista" + code, pi));
			i++;
		}
		pairs.add(new BasicNameValuePair("user", nick));
		pairs.add(new BasicNameValuePair("token", token));

		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				this.respuesta = EntityUtils.toString(rp.getEntity());
				retorno = true;
			} else {
				retorno = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Descarga la aventura con el nombreAventura del servidor
	 * 
	 * formato de la HttpResponse nombre,passAventura,mj1-12,pista1-12
	 * 
	 * se recoge la informacion en la variable String respuesta
	 * 
	 * @param nombreAventura
	 *            el nombre
	 * @param qPass
	 *            la pass
	 * @return boolean true o false en caso de exito o no
	 * 
	 */
	public boolean dameOnlineAventura(final String nombreAventura,
			final String qPass) {
		boolean retorno;
		HttpClient hc = new DefaultHttpClient();
		HttpPost post;
		// HttpPost post = new HttpPost("http://10.0.2.2/arcade.php");
		if (qPass == null) {
			post = new HttpPost("http://cinnamon.webatu.com/dameAventura.php"); // server
		} else {
			post = new HttpPost(
					"http://cinnamon.webatu.com/dameAventuraPass.php"); // server
		}
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("nombre", nombreAventura));
		pairs.add(new BasicNameValuePair("pass", qPass));
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				this.respuesta = EntityUtils.toString(rp.getEntity());
				retorno = true;
			} else {
				retorno = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Actualiza la tabla pquest del servidor
	 * 
	 * @param arraySc
	 *            array 12 casillas con puntuaciones
	 * @param nick
	 *            el nombre del jugador actual
	 * @param diferenciador
	 *            diferenciador para la tabla pquest,nombreAventura;nombreHost
	 * @param actual
	 *            minijuego actual
	 * @param token
	 *            unico para la actualizacion
	 * @return conexion o no
	 */
	public boolean updatePquest(final int[] arraySc, final String nick,
			final String diferenciador, final int actual, final String token) {
		// Vuelca toda la info de BD local en la BD web
		HttpClient hc = new DefaultHttpClient();
		boolean retorno;
		// HttpPost post = new HttpPost("http://10.0.2.2/register.php");
		HttpPost post = new HttpPost(
				"http://cinnamon.webatu.com/updatepquest.php"); // server
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		int i = 0;

		while (i < 12) {
			String punt = Integer.toString(arraySc[i]);
			int code = i + 1;
			pairs.add(new BasicNameValuePair("mj" + code, punt));
			i++;
		}
		pairs.add(new BasicNameValuePair("user", nick));
		pairs.add(new BasicNameValuePair("quest", diferenciador));
		pairs.add(new BasicNameValuePair("actual", String.valueOf(actual)));
		pairs.add(new BasicNameValuePair("token", token));
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				this.respuesta = EntityUtils.toString(rp.getEntity());
				retorno = true;
			} else {
				retorno = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Obtiene de la tabla pquest los jugadores cuya partida coincida con el
	 * diferenciador
	 * 
	 * @param diferenciador
	 *            nombreAventura;nombrehost
	 * @return conexion o no
	 */
	public Object[] getPquest(String diferenciador) {
		Object[] ret = new Object[2];
		// Vuelca toda la info de BD local en la BD web
		HttpClient hc = new DefaultHttpClient();
		boolean retorno;
		// HttpPost post = new HttpPost("http://10.0.2.2/register.php");
		HttpPost post = new HttpPost(
				"http://cinnamon.webatu.com/damepquest.php"); // server
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("quest", diferenciador));
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				ret[1] = this.respuesta = EntityUtils.toString(rp.getEntity());
				retorno = true;
			} else {
				retorno = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			retorno = false;
		}
		ret[0] = retorno;
		return ret;
	}

	/**
	 * Obtiene una notificacion del servidor en base al user
	 * 
	 * @param user
	 *            nombre del user a obtener notificacion
	 * @param token
	 *            unico para cada usuario
	 * @return true o false en caso de exito o no
	 */
	public boolean getNotif(final String user, final String token) {
		// Vuelca toda la info de BD local en la BD web
		HttpClient hc = new DefaultHttpClient();
		boolean retorno;
		// HttpPost post = new HttpPost("http://10.0.2.2/register.php");
		HttpPost post = new HttpPost("http://cinnamon.webatu.com/refquest.php"); // server
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("user", user));
		pairs.add(new BasicNameValuePair("token", token));
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				this.respuesta = EntityUtils.toString(rp.getEntity());
				retorno = true;
			} else {
				retorno = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			Launch.lanzaAviso(
					"el usuario no tiene partidas asociadas  o error inet",
					activity);
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Resetea los datos de los jugadores que esten en la partida que coincida
	 * con el nombre del host
	 * 
	 * @param nick
	 *            del usuario que ha lanzado el metodo
	 * @param diferenciador
	 *            nombreAventura;nombreHost
	 * @param token
	 *            unico que identifica al usuario
	 * @return conexion o no
	 */
	public boolean resetPquest(final String nick, final String diferenciador,
			final String token) {
		// Vuelca toda la info de BD local en la BD web
		HttpClient hc = new DefaultHttpClient();
		boolean retorno;
		// HttpPost post = new HttpPost("http://10.0.2.2/register.php");
		HttpPost post = new HttpPost(
				"http://cinnamon.webatu.com/resetpquest.php"); // server
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();

		pairs.add(new BasicNameValuePair("user", nick));
		pairs.add(new BasicNameValuePair("quest", diferenciador));
		pairs.add(new BasicNameValuePair("token", token));
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				this.respuesta = EntityUtils.toString(rp.getEntity());
				retorno = true;
			} else {
				retorno = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Obtiene los datos de un jugador concreto de nombre <code>nombre</code> de
	 * la tabla pquest
	 * 
	 * @param nombre
	 *            el nombre del jugador a obtener
	 * @return boolean true o false en caso de exito o no
	 * 
	 */
	public boolean dameJugadorPquest(final String nombre) {
		boolean retorno;
		HttpClient hc = new DefaultHttpClient();
		HttpPost post;
		// HttpPost post = new HttpPost("http://10.0.2.2/arcade.php");
		post = new HttpPost("http://cinnamon.webatu.com/damejugadorpquest.php"); // server

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("nombre", nombre));
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				this.respuesta = EntityUtils.toString(rp.getEntity());
				retorno = true;
			} else {
				retorno = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}

}

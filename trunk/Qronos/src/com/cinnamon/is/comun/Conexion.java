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

import com.cinnamon.is.game.Jugador;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

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
	 *            Contrase–a
	 * @return True ƒxito
	 * @throws ClientProtocolException
	 * @throws IOException
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
	 * Registra un usuario
	 * 
	 * @param nick
	 *            Nombre de usuario
	 * @param pass
	 *            Contrase–a
	 * @return True ƒxito
	 * @throws IOException
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
				// en principio no usamos respuesta, solo el valor de retorno
				// booleano
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
	 * Consigue el ranking del servidor y devuelve el valor obtenido
	 * 
	 * @return true o false
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
	 *            Puntuacion
	 * @return True ƒxito
	 * @throws ClientProtocolException
	 * @throws IOException
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
				// Toast.makeText(activity.getApplicationContext(), str,
				// Toast.LENGTH_SHORT).show();
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
	 * @return True ƒxito
	 * @throws ClientProtocolException
	 * @throws IOException
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
				// Toast.makeText(activity.getApplicationContext(), str,
				// Toast.LENGTH_SHORT).show();
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
	 * 
	 * 
	 * en datosAventura iran los minijuegos usados (0 no usado, 1 usado) y su
	 * correspondiente pista {mj1,pista1,mj2,pista2...} longitudes actuales mj =
	 * 1, pista = 30; nombreAv = 11, pass da igual xq la convierto a md5 32chars
	 * el post devuelve en respuesta 1 = correcto, 2 = error en el formato de
	 * envio 3 = no se pudo abrir la db
	 * 
	 * @param mj
	 * @param pista
	 * @param nombreAventura
	 * @param passAventura
	 * @param update
	 * @param nick
	 * @param token
	 * 
	 * @return boolean
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
				// Toast.makeText(activity.getApplicationContext(), str,
				// Toast.LENGTH_SHORT).show();
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
	 * descarga la aventura con el nombreAventura del servidor
	 * 
	 * formato de la HttpResponse nombre,passAventura,mj1-12,pista1-12
	 * 
	 * se recoge la informacion en la variable String respuesta
	 * 
	 * @param nombreAventura
	 *            el nombre
	 * @param qPass
	 *            la pass
	 * @return boolean
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
	 * @param arraySc
	 * @param nick
	 * @param quest
	 * @param actual
	 * @param token
	 * @return conexion o no
	 */
	public boolean updatePquest(final int[] arraySc, final String nick,
			final String quest, final int actual, final String token) {
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
		pairs.add(new BasicNameValuePair("quest", quest));
		pairs.add(new BasicNameValuePair("actual", String.valueOf(actual)));
		pairs.add(new BasicNameValuePair("token", token));
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				this.respuesta = EntityUtils.toString(rp.getEntity());
				// Toast.makeText(activity.getApplicationContext(), str,
				// Toast.LENGTH_SHORT).show();
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
	 * 
	 * @param quest
	 * @return conexion o no
	 */
	public Object[] getPquest(String quest) {
		Object[] ret = new Object[2];
		// Vuelca toda la info de BD local en la BD web
		HttpClient hc = new DefaultHttpClient();
		boolean retorno;
		// HttpPost post = new HttpPost("http://10.0.2.2/register.php");
		HttpPost post = new HttpPost(
				"http://cinnamon.webatu.com/damepquest.php"); // server
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("quest", quest));
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				ret[1]= this.respuesta = EntityUtils.toString(rp.getEntity());
				
				// Toast.makeText(activity.getApplicationContext(), str,
				// Toast.LENGTH_SHORT).show();
				retorno = true;
			} else {
				retorno = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			retorno = false;
		}ret[0]=retorno;
		return ret;
	}

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
				// Toast.makeText(activity.getApplicationContext(), str,
				// Toast.LENGTH_SHORT).show();
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
	 * @param nick
	 * @param quest
	 * @param token
	 * @return conexion o no
	 */
	public boolean resetPquest(final String nick,
			final String quest, final String token) {
		// Vuelca toda la info de BD local en la BD web
		HttpClient hc = new DefaultHttpClient();
		boolean retorno;
		// HttpPost post = new HttpPost("http://10.0.2.2/register.php");
		HttpPost post = new HttpPost(
				"http://cinnamon.webatu.com/resetpquest.php"); // server
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		int i = 0;

		pairs.add(new BasicNameValuePair("user", nick));
		pairs.add(new BasicNameValuePair("quest", quest));
		pairs.add(new BasicNameValuePair("token", token));
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				this.respuesta = EntityUtils.toString(rp.getEntity());
				// Toast.makeText(activity.getApplicationContext(), str,
				// Toast.LENGTH_SHORT).show();
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
	 * 
	 * @param nombre
	 *           el nombre del jugador a obtener
	 * @return boolean
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

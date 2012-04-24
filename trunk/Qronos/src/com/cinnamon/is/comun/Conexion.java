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

import com.cinnamon.is.game.Login;

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
	 * @param _context
	 */
	public Conexion(Activity _activity) {
		this.activity = _activity;
	}

	/**
	 * Getters
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
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public boolean login(String nick, String pass) {
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
				respuesta = EntityUtils.toString(rp.getEntity());
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
	 * @return
	 * @throws IOException
	 */
	public boolean register(String nick, String pass) {
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
				respuesta = EntityUtils.toString(rp.getEntity());
				retorno = true;
			} else
				retorno = false;
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
				respuesta = EntityUtils.toString(rp.getEntity());
				retorno = true;
			} else
				retorno = false;
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
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String toMD5(String clear) throws NoSuchAlgorithmException {
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
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public boolean updateScore(int idMJ, String nick, String score) {
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
				respuesta = EntityUtils.toString(rp.getEntity());
				// Toast.makeText(activity.getApplicationContext(), str,
				// Toast.LENGTH_SHORT).show();
				retorno = true;
			} else
				retorno = false;

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
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public boolean uploadImage(int imagen) throws IOException {
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		Bitmap uploadIMG = BitmapFactory.decodeResource(
				activity.getResources(), imagen);
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
				Toast.makeText(activity.getApplicationContext(), str,
						Toast.LENGTH_LONG).show();
				return true;
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;

	}

	public Bitmap decodificaBase64(String encodedImage) {

		byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
				decodedString.length);
		return decodedByte;
	}

	public boolean updateArcade(int[] arraySc, String nick) {
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
		// pairs.add(new BasicNameValuePair("puntuacion", score));
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				respuesta = EntityUtils.toString(rp.getEntity());
				// Toast.makeText(activity.getApplicationContext(), str,
				// Toast.LENGTH_SHORT).show();
				retorno = true;
			} else
				retorno = false;

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
	 */
	public boolean creaOnlineAventura(String[] mj, String[] pista,
			String nombreAventura, String passAventura) {
		boolean retorno;
		// Vuelca toda la info de BD local en la BD web
		HttpClient hc = new DefaultHttpClient();
		// HttpPost post = new HttpPost("http://10.0.2.2/register.php");
		HttpPost post = new HttpPost(
				"http://cinnamon.webatu.com/updateAventura.php"); // server

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
		}

		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				respuesta = EntityUtils.toString(rp.getEntity());
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
	 */
	public boolean dameOnlineAventura(String nombreAventura) {
		boolean retorno;
		HttpClient hc = new DefaultHttpClient();
		// HttpPost post = new HttpPost("http://10.0.2.2/arcade.php");
		HttpPost post = new HttpPost(
				"http://cinnamon.webatu.com/dameAventura.php"); // server
		String str = null;
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("nombre", nombreAventura));
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse rp = hc.execute(post);
			if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				respuesta = EntityUtils.toString(rp.getEntity());
				retorno = true;
			} else
				retorno = false;
		} catch (Exception e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}

}

package com.ruthlessps.net.login;

import java.nio.channels.Channel;

import org.jboss.netty.channel.ChannelHandlerContext;

import com.ruthlessps.net.packet.Packet;

/**
 * The {@link Packet} implementation that contains data used for the final
 * portion of the login protocol.
 * 
 * @author lare96 <http://github.org/lare96>
 */
public final class LoginDetailsMessage {

	/**
	 * The username of the player.
	 */
	private final String username;

	/**
	 * The password of the player.
	 */
	private final String password;

	/**
	 * The player's host address
	 */
	private final String host;

	/**
	 * The player's serial number.
	 */
	private final String serial_number;

	/**
	 * The player's client version.
	 */
	private final int clientVersion;

	/**
	 * The player's client uid.
	 */
	private final int uid;

	/**
	 * The player's client mac.
	 */
	private final String mac;

	/**
	 * Creates a new {@link LoginDetailsMessage}.
	 *
	 * @param ctx
	 *            the {@link ChannelHandlerContext} that holds our {@link Channel}
	 *            instance.
	 * @param username
	 *            the username of the player.
	 * @param password
	 *            the password of the player.
	 * @param encryptor
	 *            the encryptor for encrypting messages.
	 * @param decryptor
	 *            the decryptor for decrypting messages.
	 */
	public LoginDetailsMessage(String username, String password, String host, String serial_number, String mac,
			int clientVersion, int uid) {
		this.username = username;
		this.password = password;
		this.host = host;
		this.serial_number = serial_number;
		this.mac = mac;
		this.clientVersion = clientVersion;
		this.uid = uid;
	}

	/**
	 * Gets the player's client version.
	 * 
	 * @return the client version.
	 */
	public int getClientVersion() {
		return clientVersion;
	}

	/**
	 * Gets the password of the player.
	 * 
	 * @return the password.
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Gets the password of the player.
	 * 
	 * @return the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the player's serial number.
	 * 
	 * @return the serial number.
	 */
	public String getSerialNumber() {
		return serial_number;
	}

	/**
	 * Gets the player's client uid.
	 * 
	 * @return the client's uid.
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * Gets the username of the player.
	 * 
	 * @return the username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the mac of the player.
	 * 
	 * @return the mac.
	 */

	public String getMac() {
		return mac;
	}

}
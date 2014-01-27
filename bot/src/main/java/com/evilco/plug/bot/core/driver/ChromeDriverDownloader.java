package com.evilco.plug.bot.core.driver;

import com.evilco.plug.bot.core.Bot;
import com.evilco.plug.bot.core.utility.Architecture;
import com.evilco.plug.bot.core.utility.Platform;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ChromeDriverDownloader implements Runnable {

	/**
	 * Defines the prefix for all chrome downloads.
	 */
	public static final String FILE_PREFIX = "http://chromedriver.storage.googleapis.com/2.8/chromedriver_";

	/**
	 * Defines the suffix for all chrome downloads.
	 */
	public static final String FILE_SUFFIX = ".zip";

	/**
	 * Internal logging instance.
	 */
	protected static final Logger logger = Logger.getLogger ("ChromeDriverDownloader");

	/**
	 * Returns the download URL.
	 * @return
	 */
	public URL getDownloadURL () {
		// create url
		String fileURL = FILE_PREFIX;

		// add system
		switch (Platform.guessPlatform ()) {
			case LINUX:
			case SOLARIS:
				fileURL += "linux";

				// add architecture
				switch (Architecture.guessArchitecture ()) {
					case x64:
						fileURL += "64";
						break;
					case x86:
						fileURL += "32";
						break;
					default:
						throw new RuntimeException ("Your system architecture is not supported by the chrome driver. Please switch to a different driver.");
				}
				break;
			case MAC_OS_X:
				fileURL += "mac32";
				break;
			case WINDOWS:
				fileURL += "win32";
				break;
			default:
				throw new RuntimeException ("Your system is not supported by the chrome driver. Please switch to a different driver.");
		}

		// add suffix
		fileURL += FILE_SUFFIX;

		// construct URL
		try {
			return (new URL (fileURL));
		} catch (MalformedURLException ex) {
			throw new RuntimeException ("Could not create a valid download URL.", ex);
		}
	}

	/**
	 * Returns the correct driver file.
	 * @return
	 */
	public File getDriverFile () {
		String fileName = "chromedriver";

		// get platform
		if (Platform.guessPlatform () == Platform.WINDOWS) fileName += ".exe";

		// create driver file reference
		return (new File (Bot.getLibraryDirectory (), fileName));
	}

	/**
	 * Checks whether the driver has already been downloaded.
	 * @return
	 */
	public boolean exists () {
		return (this.getDriverFile ().exists ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run () {
		// find download URL
		URL downloadURL = this.getDownloadURL ();

		logger.info ("Downloading chrome driver (native executable) from " + downloadURL.toString () + " ...");

		// open input stream
		InputStream in = null;
		OutputStream out = null;

		File zipFile = new File (this.getDriverFile ().getParentFile (), "chrome-driver.zip");

		try {
			in = downloadURL.openStream ();
			out = new FileOutputStream (zipFile);

			// download data
			IOUtils.copy (in, out);
		} catch (IOException ex) {
			throw new RuntimeException ("Could not download the appropriate chrome driver (native executable) for your current platform.", ex);
		} finally {
			// close streams
			try {
				in.close ();
			} catch (Exception ignore) { }

			try {
				out.close ();
			} catch (Exception ignore) { }
		}

		// extract file
		ZipFile file = null;
		InputStream zipIn = null;
		OutputStream exeOut = null;

		try {
			file = new ZipFile (zipFile);
			ZipEntry entry = file.getEntry (this.getDriverFile ().getName ());

			// get streams
			zipIn = file.getInputStream (entry);
			exeOut = new FileOutputStream (this.getDriverFile ());

			// copy data
			IOUtils.copy (zipIn, exeOut);

			// make file executable
			this.getDriverFile ().setExecutable (true);
		} catch (IOException ex) {
			throw new RuntimeException ("Could not extract chrome driver.", ex);
		} finally {
			// close streams
			try {
				file.close ();
			} catch (Exception ignore) { }
		}

		// log
		logger.info ("Finished downloading the chrome driver executable.");
	}
}

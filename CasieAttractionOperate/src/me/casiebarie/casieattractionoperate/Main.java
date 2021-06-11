package me.casiebarie.casieattractionoperate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	public API api;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		LoadSample();
		
		Functions f = new Functions(this);
		CAO cao = new CAO(this, f);
		new InfoCommand(this, f);
		new TabComplete(this, f);
		api = new API(this, cao, f);
		
		//UpdateChecker
		new UpdateChecker(this, 93203).getVersion(version -> {
			if(this.getDescription().getVersion().equalsIgnoreCase(version)) {
				this.getLogger().info("You are using the most recent version. (v" + this.getDescription().getVersion() + ")");
			} else {
				this.getLogger().info("There is a new update available (v" + version + ")!   You are using: v" + this.getDescription().getVersion());
			}
		});
	}
	
	@Override
	public void onDisable() {
		saveDefaultConfig();
		reloadConfig();
	}

	//LoadSample
	public void LoadSample() {
		File sampleFolder = new File(getDataFolder(), "Attractions");
		File sampleFile = new File(sampleFolder, "Sample.yml");
		
		if(!sampleFile.exists()) {
			this.getLogger().info("Sample file not found! Making one.");
			sampleFolder.mkdir();
			try {sampleFile.createNewFile();
			} catch (Exception e) {e.printStackTrace();}
		}
		copy(getResource("sample.yml"), sampleFile);
	}
	public void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while((len=in.read(buf))>0) {out.write(buf,0,len);}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

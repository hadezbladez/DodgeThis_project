package com.essential;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;

import javax.swing.JFrame;

import com.addition.KeyBoardManager;
import com.addition.WindowsEventInformation;
import com.state.StateManager;
import com.state.level.StandardModeState;

public class GameEngine implements Runnable{
	
	////System variables////
	//path checks
	private final static int DEVPATH_ = 123000, PRODUCTPATH_ = 124000;
	private String mainPathFolders;
	//more in-depth
	private ServerSocket programServerSocket;
	//monitor, window size specification
	private DisplayMode dispMode;
	private GraphicsEnvironment gEnv; private GraphicsDevice gDev;
	private GraphicsConfiguration gCon;
	public static String TITLE;
	public static int windowFrame_width, windowFrame_height;
	public static int gameFrame_width, gameFrame_height;
	private static int gameFrame_x, gameFrame_y;
	private static double gameFrame_scaleWidth, gameFrame_scaleHeight;
	private final int monitor_width, monitor_height;
	
	//timing and function for looping
	private int fps;
	private BufferStrategy bufferStrat; private Graphics graphicRender;
	private double renderTiming, renderDuration_function;

	//core rendering properties
	private JFrame windowFrame; private Canvas canvas; private Thread thread;
	private boolean ready, executing, running;
	private int notRunning_reason;
	public final static int NULL_REASON = 0, EXITING = 555000;
	public final static int JFRAME_CONFIGURATION_toFullScreen = 555001, JFRAME_CONFIGURATION_toFullScreenFatSize = 555002, JFRAME_CONFIGURATION_toNormalScreen = 555003;
	private final static int SAFETY_FIRST_MODE = 12200, SAFETY_FIRST_HANGSEC_MODE = 13300, CANCER_RISK_MODE = 21100;
	private final static int COMBINE_updateAndRender = 22100, SEPARATE_updateAndRender = 22200, LIMITupdate_MAXIMUMrender = 22300;
	private int loop_memoryProcess_Model; public final static int memorySafe = 33000, fullThrotle = 34000;
	
	//input properties
	private WindowsEventInformation windowInformationMemory;
	private KeyBoardManager keyInput;
	
	private StateManager stateManager;
	public static GameEngine INSTANCE;
	public static Assets assets; 
	
	////main branches
	public GameEngine() {
		//check hardwares
		OS_check();
		pathFile_check(DEVPATH_);
		server_checks();
		monitorGraphicHardware_checks();
		
		TITLE = "Dodge This!"; 
		monitor_width = dispMode.getWidth(); monitor_height = dispMode.getHeight();
		windowSizeSpecification_RenderingProperties_preferences(0, 400 + 6, 600 + 28, 0, 0, 200, 300, 2, 2);
		preInit(); threadSleeping(50);
		threadBuildUp(); threadSleeping(50);
		threadStart();
		
		threadJoin();
		closingSequence_runRF(0);
		
	}
	@Override public void run() {
		
		System.out.println("Engine.run() is thread is alive = " + thread.isAlive() );
		threadSleeping(500);
		
		//customizable according to input game specification beforehand
		if(windowFrame != null){windowFrame.setVisible(true);}
		if(canvas != null){
			canvas.requestFocus();
			canvas.createBufferStrategy(3);
			bufferStrat = canvas.getBufferStrategy();
		}
		
		while(executing){
			programRuns_mainExecution(SAFETY_FIRST_HANGSEC_MODE, COMBINE_updateAndRender);
			programRuns_reasonExecution();
		}
		
	}
	private void programRuns_reasonExecution(){
		if(notRunning_reason == JFRAME_CONFIGURATION_toFullScreen){}
		else if(notRunning_reason == JFRAME_CONFIGURATION_toFullScreenFatSize){}
		else if(notRunning_reason == JFRAME_CONFIGURATION_toNormalScreen){}
		else if(notRunning_reason == EXITING){executing = false;}
		else{executing = false; /*with error*/}
	}
	private void programRuns_mainExecution(int safeMethodProgramRuns, int loopingPreferences){
		if(safeMethodProgramRuns == CANCER_RISK_MODE){gameLoop(loopingPreferences);}
		else if(safeMethodProgramRuns == SAFETY_FIRST_MODE){
			try {gameLoop(loopingPreferences);}
			catch (Exception e) {
				System.err.println("Engine.run() get out man");
				e.printStackTrace(); running = false;
			}
		}
		else if(safeMethodProgramRuns == SAFETY_FIRST_HANGSEC_MODE){
			try {gameLoop(loopingPreferences);}
			catch (Exception e) {
				System.err.println("Engine.run() get out man");
				e.printStackTrace(); running = false;
				threadSleeping(1000);
			}
		}
	}
	private void gameLoop(int loopingPreferences){
		//init variable
		long now = 0; long lastTime = System.nanoTime();
		long deltaTime = 0; long deltaFixComb = 0; long nanoSecond = 1000000000l;
		threadSleeping(1);
		//try to not use double delta ok?
		while(running){
			if(loopingPreferences == COMBINE_updateAndRender){//using this doesnt change fps
				now = System.nanoTime();
				deltaFixComb += ((now - lastTime) * fps) ;
				lastTime = now;
				systemControls();
				
				if(deltaFixComb >= nanoSecond){
					updateMechanism(loopingPreferences, 0);
					renderMechanism(loopingPreferences, 0, false);
					deltaFixComb -= nanoSecond;
				}
				
				if(loop_memoryProcess_Model == memorySafe){threadSleeping(1); Thread.yield();}//<< doesnt cause frameRate drops
				threadSleeping(1);
			}
			else if(loopingPreferences == LIMITupdate_MAXIMUMrender){}
			else if(loopingPreferences == SEPARATE_updateAndRender){//using this does change fps
				//timings
				now = System.nanoTime();
				deltaTime = now - lastTime;
				lastTime = now;
				systemControls();
				
				updateMechanism(loopingPreferences, deltaTime);
				renderMechanism(loopingPreferences, deltaTime, false);
				
				if(loop_memoryProcess_Model == memorySafe){threadSleeping(1); Thread.yield();}//<< optional and causing frameRate drops
			}
		}
		
	}

	
	////non-main branch
	//main
	private void pathFile_check(int pathUniformInitial){
		//string path that path null(can't get detected) or get

		
		String filePath_thisClass = getClass().getResource("").toString();
		String jarStart_name = new java.io.File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath() ).getName();
		String path_check = null;
		
		if(pathUniformInitial == DEVPATH_){
			path_check = filePath_thisClass.subSequence(6, filePath_thisClass.lastIndexOf(jarStart_name) ).toString();
			
			if(path_check.substring(1, 3).equals(":/") ){
				mainPathFolders = path_check;
				mainPathFolders = mainPathFolders.replaceAll("%20", " ");
			}
			else{mainPathFolders = "NULL cant access or there is something wrong"; return;}
		}
		else if(pathUniformInitial == PRODUCTPATH_){
			path_check = filePath_thisClass.subSequence(10, filePath_thisClass.lastIndexOf(jarStart_name) ).toString();
			
			if(path_check.substring(1, 3).equals(":/") ){
				mainPathFolders = path_check;
				mainPathFolders = mainPathFolders.replaceAll("%20", " ");
			}
			else{mainPathFolders = "NULL cant access or there is something wrong"; return;}
		}
		
		
		
	}
	private void OS_check(){
		//check os
		String osName = System.getProperties().getProperty("os.name");
		if(osName.equals("Windows 7") ){}
		else if(osName.startsWith("Win") ){}
		else {System.exit(-1);}
		
		System.out.println("GameEngine.OS_check() os name = " + osName);
		System.out.println("=============================================");
	}
	private void server_checks(){
		try {//server check
			//well i think this server is only 4 code testing ||  program run once
			programServerSocket = new ServerSocket(65535, 1, InetAddress.getLocalHost() );
			System.out.println("Engine.serverChecks() local port = " + programServerSocket.getLocalPort() );
			System.out.println("Engine.serverChecks() host name = " + programServerSocket.getInetAddress().getHostName() );
		}
		catch (SocketException e){//it might be show the pop-ups program that is already in use
			System.out.println("Engine.serverChecks() + programs already running");
			e.printStackTrace();
			System.exit(1);
		}
		catch (IOException e) {e.printStackTrace();}
	}
	private void monitorGraphicHardware_checks(){
		//the first initialize
		gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gDev = gEnv.getDefaultScreenDevice();
		gCon = gDev.getDefaultConfiguration(); dispMode = gDev.getDisplayMode();
		
		System.out.println("GameEngine.GameEngine() checking graphic_device = ");
		System.out.println("translucent = " + gDev.isWindowTranslucencySupported(WindowTranslucency.TRANSLUCENT) );
		System.out.println("perpixel_translucent = " + gDev.isWindowTranslucencySupported(WindowTranslucency.PERPIXEL_TRANSLUCENT) );
		System.out.println("perpixel_transparent = " + gDev.isWindowTranslucencySupported(WindowTranslucency.PERPIXEL_TRANSPARENT) );
		System.out.println("display change = " + gDev.isDisplayChangeSupported() );
		System.out.println("full screen = " + gDev.isFullScreenSupported() );
		
		System.out.println("translucency capable = " + gCon.isTranslucencyCapable() );
		
		System.out.println();
		
		System.out.println("GameEngine.GameEngine() checking display mode detail = ");
		System.out.println("bit depth = " + dispMode.getBitDepth() );
		System.out.println("refresh rate = " + dispMode.getRefreshRate() );
		
		System.out.println();
	}
	private void windowSizeSpecification_RenderingProperties_preferences(int screenSizeModels, int windowFrame_width, int windowFrame_height,
			int gameFrameTranslate_x, int gameFrameTranslate_y, int gameFrame_width, int gameFrame_height, double scaleGameFrame_width, double scaleGameFrame_height){
		
		if(GameEngine.windowFrame_width > monitor_width){
			windowFrame_width = monitor_width;
			System.err.println("Engine.windowSize_specificationPreferences() windowFrame width too big");
		}
		if(GameEngine.windowFrame_height > monitor_height){
			windowFrame_height = monitor_height;
			System.err.println("Engine.windowSize_specificationPreferences() windowFrame height too big");
		}
		
		if(screenSizeModels == 0 || screenSizeModels == 1){
			GameEngine.windowFrame_width = windowFrame_width; GameEngine.windowFrame_height = windowFrame_height;
			
			gameFrame_x = gameFrameTranslate_x; gameFrame_y = gameFrameTranslate_y;
			GameEngine.gameFrame_width = gameFrame_width;GameEngine.gameFrame_height = gameFrame_height;
			GameEngine.gameFrame_scaleWidth = scaleGameFrame_width; GameEngine.gameFrame_scaleHeight = scaleGameFrame_height;
			
			if(GameEngine.windowFrame_width - 6 != (GameEngine.gameFrame_width * GameEngine.gameFrame_scaleWidth) &&
					GameEngine.windowFrame_height - 28 != (GameEngine.gameFrame_height * GameEngine.gameFrame_scaleHeight) ){
				System.err.println("Engine.gameSpecification_preferences() warning! window frame =/= game frame scale");
			}
		}
		else if(screenSizeModels > 1){}
		
		//initialize frame, canvas and dimension
		canvas = new Canvas(gDev.getDefaultConfiguration() );
		windowFrame = new JFrame(gDev.getDefaultConfiguration() );
		
		Dimension windowDimenision = new Dimension(windowFrame_width, windowFrame_height);
		
		/*ArrayList<BufferedImage> icons = new ArrayList<BufferedImage>();
		icons.add(ImageLoader.loadImage_static(getClass().getResourceAsStream("/gem64x64.png") ) );
		icons.add(ImageLoader.loadImage_static(getClass().getResourceAsStream("/gem32x32.png") ) );
		icons.add(ImageLoader.loadImage_static(getClass().getResourceAsStream("/gem16x16.png") ) );
		windowFrame.setIconImages(icons);//not used for multiple icons
		icons.clear();icons = null;
		*/
		//JFrame.setDefaultLookAndFeelDecorated(false);//<<what this shit?
		//canvas.setBounds(0, 0, 800, 600);//what is this?
		//canvas.setMaximumSize(new Dimension(game_width, game_height) );
		//canvas.setMinimumSize(new Dimension(game_width, game_height) );
		//canvas.setPreferredSize(new Dimension(game_width, game_height) );
		canvas.setIgnoreRepaint(true);
		
		windowFrame.setIgnoreRepaint(true);
		windowFrame.setAlwaysOnTop(false);
		windowFrame.setTitle(TITLE);
		windowFrame.setSize(windowDimenision);
		windowFrame.setResizable(false);//no resize
		windowFrame.setLocationRelativeTo(null);//center screen
		windowFrame.setFocusable(true);
		
		windowInformationMemory = new WindowsEventInformation(this);
		windowFrame.addWindowListener(windowInformationMemory);
		switch(screenSizeModels){
			case 0 : windowFrame.setUndecorated(false); break;
			case 1 : if(gDev.isFullScreenSupported() ){windowFrame.setUndecorated(true);} break;
			case 2 : if(gDev.isFullScreenSupported() ){windowFrame.setUndecorated(true); windowFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);} break;
			case 3 : if(gDev.isFullScreenSupported() ){windowFrame.setUndecorated(true); gDev.setFullScreenWindow(windowFrame);} break;
		}
		windowFrame.add(canvas);//solve the lag issue using canvas or no
		
		for(int i = 0; i < windowFrame.getComponents().length; i++){
			System.out.println("GameEngine.GameEngine() component = " + windowFrame.getComponent(i) );
		}
		System.out.println();
		
		ready = true;
	}
	
	private void preInit(){
		assets = null;
		//mainMemory = null;
		INSTANCE = this;
		
		//TODO state-ing
		stateManager = new StateManager();
		stateManager.goNewState(new StandardModeState(stateManager) );
		
		fps = 60; loop_memoryProcess_Model = fullThrotle;
		renderDuration_function = (1000 * 1000 * 1000) / (fps);
		
		//keyboard input
		keyInput = new KeyBoardManager();
		canvas.addKeyListener(keyInput);
	}
	private void threadBuildUp(){
		if(!ready){return;}
		if(executing){return;} if(running){return;}
		
		thread = new Thread(this, TITLE + " main-thread");
		executing = true; running = true;
	}
	private void threadStart(){
		if(thread == null){return;}
		thread.start();
	}
	
	private void closingSequence_runRF(int exitStatusNumber){
		//exitStatusNumber is not determined but its fully customizable
		
		//destroy all preinit()methods
		threadSleeping(100);
		if(bufferStrat != null){bufferStrat.dispose(); bufferStrat = null;}
		if(graphicRender != null){graphicRender.dispose(); graphicRender = null;}
		if(canvas != null){canvas.removeNotify(); canvas.setEnabled(false);}
		if(windowFrame != null){windowFrame.removeNotify(); windowFrame.dispose(); windowFrame.removeAll();}
		threadSleeping(100);
		//server close
		if(programServerSocket != null){
			try {
				programServerSocket.close();
				if(programServerSocket.isClosed() ){programServerSocket = null; System.out.println("GameEngine.closingSequence_runRF() + server closed");}
				else if(!programServerSocket.isClosed() ){programServerSocket.close(); programServerSocket = null; System.err.println("GameEngine.closingSequence_runRF() + something error when server closed");}
			}
			catch (IOException e) {e.printStackTrace(); System.exit(1);}
		}
		
	}


	////update methods
	//main
	private void threadJoin(){
		if(thread == null){return;}
		
		try {thread.join();}
		catch (InterruptedException e) {System.err.print("GameEngine.threadJoin() "); e.printStackTrace();}
	}
	public void threadSleeping(long millis){
		try {Thread.sleep(millis);}
		catch (InterruptedException e) {e.printStackTrace();}
	}
	public void notRunning(int reasonToDo){
		if(reasonToDo == NULL_REASON){return;}
		notRunning_reason = reasonToDo;
		running = false;
	}
	
	private void updateMechanism(int loopingPreferences, long deltaTime){
		if(loopingPreferences == COMBINE_updateAndRender){processUpdate();}
		else if(loopingPreferences == SEPARATE_updateAndRender){processUpdate(deltaTime);}
	}
	private void renderMechanism(int loopingPreferences, long deltaTime, boolean needYield){
		if(loopingPreferences == COMBINE_updateAndRender){
			try {
				graphicRender = bufferStrat.getDrawGraphics();
				drawOption(true);drawReference();drawEnd();
				if(needYield){Thread.yield();}
			}
			catch (Exception e) {e.printStackTrace();}
			//finally{if(bufferStrat != null){bufferStrat.getDrawGraphics().dispose();} }
		}
		else if(loopingPreferences == SEPARATE_updateAndRender){
			//fps ( 56 - 57) and pps (1000 - 990) << its because threadSleeping
			if(renderTiming < renderDuration_function){renderTiming += deltaTime;}
			else if(renderTiming > 2 * renderDuration_function){renderTiming = 0; System.err.println("Engine.render() some error rendering");}
			else if(renderTiming >= renderDuration_function){
				renderTiming -= renderDuration_function;
				try {
					graphicRender = bufferStrat.getDrawGraphics();
					drawOption(true);drawReference();drawEnd();
					if(needYield){Thread.yield();}
				}
				catch (Exception e) {e.printStackTrace();}
				//finally{if(bufferStrat != null){bufferStrat.getDrawGraphics().dispose();} }
			}
		}
		
	}
	
	private void systemControls(){}
	private void processUpdate(long deltaTime){}
	private void processUpdate(){
		if(stateManager != null){stateManager.update();}
	}
	private void drawOption(boolean clearScreenBoolean){//customizable
		//it really is making the game awesome || optional
		if(graphicRender instanceof Graphics2D){
			//((Graphics2D)graphicRender).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			((Graphics2D)graphicRender).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		}
		if(clearScreenBoolean){
			//before translate amd scaled || clear screen || customizable
			graphicRender.setClip(0, 0, windowFrame_width - 6, windowFrame_height - 28);
			graphicRender.clearRect(0, 0, windowFrame_width - 6, windowFrame_height - 28);
		}
		((Graphics2D)graphicRender).translate(gameFrame_x, gameFrame_y);
		((Graphics2D)graphicRender).scale(gameFrame_scaleWidth, gameFrame_scaleHeight);
		//clip images that doesnt goes outside
		graphicRender.setClip(0, 0, gameFrame_width, gameFrame_height);
	}
	private void drawReference(){//focus drawing here || it means customizable
		if(stateManager != null){stateManager.render(graphicRender);}
	}
	private void drawEnd(){
		bufferStrat.show();
		graphicRender.dispose(); 
		/*
		if(bufferStrat != null && graphicRender != null){
			if(!bufferStrat.contentsLost() ){}
			else if(bufferStrat.contentsLost() ){System.out.println("Engine.drawEnd() buffer strategy contents are lost"); /*exit demand here}
		}
		*/
	}
	
	
	
	
	
	
}


























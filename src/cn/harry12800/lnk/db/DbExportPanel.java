package cn.harry12800.lnk.db;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import chrriis.dj.nativeswing.swtimpl.components.JDirectoryDialog;
import chrriis.dj.nativeswing.swtimpl.components.JFileDialog;
import cn.harry12800.Lnk.core.Context;
import cn.harry12800.Lnk.core.CorePanel;
import cn.harry12800.Lnk.core.FunctionPanelConfig;
import cn.harry12800.Lnk.core.FunctionPanelModel;
import cn.harry12800.dbhelper.DBType;
import cn.harry12800.dbhelper.Db;
import cn.harry12800.dbhelper.MysqlHelper;
import cn.harry12800.dbhelper.OracleHelper;
import cn.harry12800.dbhelper.entity.DBTable;
import cn.harry12800.developer.DeveloperUtils;
import cn.harry12800.j2se.action.CtrlSAction;
import cn.harry12800.j2se.component.InputText;
import cn.harry12800.j2se.component.MButton;
import cn.harry12800.j2se.component.TextLabel;
import cn.harry12800.j2se.component.TextLabel.Builder;
import cn.harry12800.j2se.component.btn.ClickAction;
import cn.harry12800.j2se.component.panel.AreaTextPanel;
import cn.harry12800.j2se.dialog.TextScanDialog;
import cn.harry12800.j2se.style.UI;
import cn.harry12800.j2se.utils.Clip;
import cn.harry12800.tools.FileUtils;
@FunctionPanelModel(configPath = "db",
	height =  6 * 32 + 25+210, width = 350,
	backgroundImage = "db_back.jpg", headerImage = "db.png",
	desc = "数据库的常用操作\r\noracle支持的全面一点。")
@FunctionPanelConfig(filename = "db.json")
public class DbExportPanel extends CorePanel <DbJsonConfig> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DbConnectionParam dbExport;
	InputText userNameInput;
	InputText passInput;
	InputText urlInput;
	private int width = 350;
	/**
	 * 向上切换数据库链接按钮
	 */
	MButton up = new MButton("上", 70, 30);
	/**
	 * 向下切换数据库链接按钮
	 */
	MButton down = new MButton("下", 70, 30);
	MButton newConn = new MButton("新增", 70, 30);
	MButton delConn = new MButton("删除", 70, 30);
	MButton updateConn = new MButton("修改", 70, 30);
	MButton exportWord = new MButton("导出Word", 70, 30);
	MButton exportComment = new MButton("导出注释", 70, 30);
	MButton exportTable = new MButton("建表语句", 70, 30);
	MButton impComment = new MButton("导入注释", 70, 30);
	MButton test = new MButton("测试链接", 70, 30);
	MButton execute = new MButton("执行", 70, 30);
	List<DbConnectionParam> dbExports;
	AreaTextPanel areaTextPanel = new AreaTextPanel();
	private Context context;
	public static int currIndex =-1;
	public DbExportPanel(Context context) {
		super(context);
		try {
			this.context = context;
			this.dbExports = getConfigObject().getList();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (dbExports != null && dbExports.size() > 0) {
			currIndex =	0 ;
			this.dbExport = dbExports.get(0);
		} else {
			currIndex= -1;
			this.dbExport = new DbConnectionParam();
		}
		setBackground(UI.backColor);
		setLayout(null);
//		new DragListener(this);
		
		setSize(width, 6 * 32 + 25+210);
		areaTextPanel.setBounds(1, 220, 345, 200);
		add(areaTextPanel);
		Builder createBgColorBuilder = TextLabel
				.createBgColorBuilder(UI.backColor(0));
		createBgColorBuilder.hasborder = false;
		createBgColorBuilder.align = 5;
		createBgColorBuilder.borderRadius = 0;
		TextLabel userName = new TextLabel("用户名", 50, 30, createBgColorBuilder);

		userNameInput = new InputText(30);

		createBgColorBuilder = TextLabel.createBgColorBuilder(UI.backColor(0));
		createBgColorBuilder.hasborder = false;
		createBgColorBuilder.align = 5;
		createBgColorBuilder.borderRadius = 0;
		TextLabel pass = new TextLabel("密  码", 50, 30, createBgColorBuilder);

		passInput = new InputText(30);

		createBgColorBuilder = TextLabel.createBgColorBuilder( UI.backColor(0));
		createBgColorBuilder.hasborder = false;
		createBgColorBuilder.align = 5;
		createBgColorBuilder.borderRadius = 0;
		TextLabel url = new TextLabel("地  址", 50, 30, createBgColorBuilder);
		urlInput = new InputText(60, 16);


		userName.setBounds(5, 25, 50, 25);
		userNameInput.setBounds(60, 25, 180, 25);
		pass.setBounds(5, 50, 50, 25);
		passInput.setBounds(60, 50, 180, 25);
		url.setBounds(5, 75, 50, 25);
		urlInput.setBounds(5, 100, 335, 25);

		exportWord.setBounds(250, 160, 70, 25);
		exportComment.setBounds(170, 160, 70, 25);
		impComment.setBounds(90, 160, 70, 25);
		exportTable.setBounds(10, 160, 70, 25);
		
		up.setBounds(250, 25, 70, 25);
		down.setBounds(250, 50, 70, 25);
		
		test.setBounds(10, 130, 70, 25);
		newConn.setBounds(90, 130, 70, 25);
		updateConn.setBounds(170, 130, 70, 25);
		delConn.setBounds(250, 130, 70, 25);

		execute.setBounds(250, 185, 70, 25);
		
		userNameInput.setText(this.dbExport.getUserName());
		passInput.setText(this.dbExport.getPass());
		urlInput.setText(this.dbExport.getUrl());

		add(userName);
		add(userNameInput);
		add(pass);
		add(passInput);
		add(url);
		add(urlInput);
		add(exportWord);
		add(exportComment);
		add(exportTable);
		add(execute);
		

		add(up);
		add(down);
		add(test);
		add(newConn);
		add(delConn);
		add(updateConn);
		
		add(impComment);
		addAction();
		String path = dirPath+File.separator+ "db.log";
		if(new File(path).exists()){
			try {
				areaTextPanel.setText(FileUtils.getSrcByFilePath(path,"utf-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		areaTextPanel.setCtrlSAction(new CtrlSAction() {
			public void ctrlS() {
				executeSql();
			}
		});
	}

	private void addAction() {
		impComment.addMouseListener(new ClickAction(exportWord) {
			@Override
			public void leftClick(MouseEvent e) {
				String user = userNameInput.getText().trim();
				String pwd = passInput.getText().trim();
				String url = urlInput.getText().trim();
				/**
				 * oracle 的数据库生成数据字典工具
				 */
				Db db;
				if (url.contains("mysql"))
					db = new MysqlHelper();
				else {
					db = new OracleHelper();
				}
				JFileDialog dialog = new JFileDialog();
				dialog.setSelectedFileName(user+".txt");
				dialog.show(DbExportPanel.this);
				String selectedFileName = dialog.getSelectedFileName();
				if(selectedFileName==null){
					return ;
				}
				String parentDirectory = dialog.getParentDirectory();
				selectedFileName = parentDirectory+File.separator+selectedFileName;
				System.out.println(selectedFileName);
				String sql = FileUtils.getSrcByFilePath(selectedFileName);
				System.out.println(sql);
				try {
//					db.exeSql(sql);
					db.exeSql(url,user,pwd,sql);
				} catch (Exception e1) {
					alert(e1.getMessage());
				}
				
			}
		});
		exportWord.addMouseListener(new ClickAction(exportWord) {
			@Override
			public void leftClick(MouseEvent e) {
				String user = userNameInput.getText().trim();
				String pwd = passInput.getText().trim();
				String url = urlInput.getText().trim();
				/**
				 * oracle 的数据库生成数据字典工具
				 */
				Db db;
				if (url.contains("mysql"))
					db = new MysqlHelper();
				else {
					db = new OracleHelper();
				}
				JDirectoryDialog t = new JDirectoryDialog();
				t.show( DbExportPanel.this);
				String selectedDirectory = t.getSelectedDirectory();
				if(selectedDirectory==null)return ;
				try {
					db.generateDescFile(url, user, pwd,selectedDirectory);
					Clip.openFile(selectedDirectory);
					Runtime runtime = Runtime.getRuntime();
					runtime.exec("explorer /select, " + selectedDirectory+File.separator + user + ".docx");
				} catch (Exception e1) {
					e1.printStackTrace();
					alert(e1.getMessage());
				}
			}
		});
		exportComment.addMouseListener(new ClickAction(exportComment) {
			@Override
			public void leftClick(MouseEvent e) {
				try {
					String user = userNameInput.getText().trim();
					String pwd = passInput.getText().trim();
					String url = urlInput.getText().trim();
					
					JFileDialog dialog = new JFileDialog();
					dialog.setSelectedFileName(user+"_comment.txt");
					dialog.show(DbExportPanel.this);
					String selectedFileName = dialog.getSelectedFileName();
					if(selectedFileName==null){
						return ;
					}
					String parentDirectory = dialog.getParentDirectory();
					selectedFileName = parentDirectory+File.separator+selectedFileName;
					System.out.println(selectedFileName);
					
					List<DBTable> dbTable = DeveloperUtils.getDBTable(url, user, pwd);
					StringBuffer sBuffer  = new StringBuffer();
					for (  DBTable table  : dbTable) {
					 	sBuffer.append(table.getCreateCommentDDL(DBType.ORACLE)).append("\r\n");
					}
					FileUtils.writeContent(selectedFileName,sBuffer.toString());
					Clip.openFile(selectedFileName);
					//new TextScanDialog(sBuffer.toString());
				} catch (Exception e1) {
					alert(e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		exportTable.addMouseListener(new ClickAction(exportTable) {
			@Override
			public void leftClick(MouseEvent e) {
				try {
					String user = userNameInput.getText().trim();
					String pwd = passInput.getText().trim();
					String url = urlInput.getText().trim();
					List<DBTable> dbTable = DeveloperUtils.getDBTable(url, user, pwd);
					StringBuffer sBuffer  = new StringBuffer();
					for (  DBTable table  : dbTable) {
						if(url.contains("mysql"))
							sBuffer.append(table.getCreateDDL(DBType.MYSQL)).append("\r\n");
						else{
							sBuffer.append(table.getCreateDDL(DBType.ORACLE)).append("\r\n");
						}
					}
					new TextScanDialog(sBuffer.toString());
				} catch (Exception e1) {
					alert( e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		test.addMouseListener(new ClickAction(test) {
			@Override
			public void leftClick(MouseEvent e) {
				try {
					String user = userNameInput.getText().trim();
					String pwd = passInput.getText().trim();
					String url = urlInput.getText().trim();
					Db db;
					if (url.contains("mysql"))
						db = new MysqlHelper();
					else {
						db = new OracleHelper();
					}
					boolean connent = db.testConnection(url, user, pwd);
					if(connent){
						 alert("链接成功！");
					}else{
						 alert("链接失败！");
					}
				} catch (Exception e1) {
					 alert(e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		newConn.addMouseListener(new ClickAction(newConn) {
			@Override
			public void leftClick(MouseEvent e) {
				String user = userNameInput.getText().trim();
				String pwd = passInput.getText().trim();
				String url = urlInput.getText().trim();
				DbConnectionParam e1 = new DbConnectionParam();
				e1.setUserName(user);
				e1.setUrl(url);
				e1.setPass(pwd);
				dbExports.add(e1);
				saveConfigObject();
				context.refresh(DbExportPanel.this);
				currIndex+=1;
				alert("新增成功！");
			}
		});
		delConn.addMouseListener(new ClickAction(delConn) {
			@Override
			public void leftClick(MouseEvent e) {
				if(currIndex==-1){
					 alert("没有可删除的链接！");
				}else {
					DbExportPanel.this.dbExports.remove(currIndex);
					currIndex-=1;
					saveConfigObject();
					DbExportPanel.this.setCurrConn(currIndex);
					context.refresh(DbExportPanel.this);
					 alert("删除成功！");
				}
			}
		});
		updateConn.addMouseListener(new ClickAction(updateConn) {
			@Override
			public void leftClick(MouseEvent e) {
				//jdbc:oracle:thin:@192.168.0.159:1521:orcl
				String user = userNameInput.getText().trim();
				String pwd = passInput.getText().trim();
				String url = urlInput.getText().trim();
				DbConnectionParam e1 = new DbConnectionParam();
				e1.setUserName(user);
				e1.setUrl(url);
				e1.setPass(pwd);
				DbExportPanel.this.dbExports.set(currIndex, e1);
				saveConfigObject();
				context.refresh(DbExportPanel.this);
				 alert("修改成功！");
			}
		});
		down.addMouseListener(new ClickAction(down) {
			@Override
			public void leftClick(MouseEvent e) {
				if(currIndex==-1){
					 alert("没有可切换的链接！");
				}else {
					currIndex+=1;
					if(currIndex==DbExportPanel.this.dbExports.size()){
						currIndex = 0 ;
					}
					DbExportPanel.this.setCurrConn(currIndex);
				}
			}
		});
		up.addMouseListener(new ClickAction(up) {
			@Override
			public void leftClick(MouseEvent e) {
				//jdbc:oracle:thin:@192.168.0.159:1521:orcl
				if(currIndex==-1) {
					 alert("没有可切换的链接！");
				}else {
					currIndex-=1;
					if(currIndex ==-1)
						currIndex = DbExportPanel.this.dbExports.size()-1;
					DbExportPanel.this.setCurrConn(currIndex);
				}
			}
		});
		execute.addMouseListener(new ClickAction(execute) {
			@Override
			public void leftClick(MouseEvent e) {
				executeSql();
			}
		});
	}

	protected void executeSql() {
		if(currIndex<0)return ;
		DbConnectionParam dbExportType = dbExports.get(currIndex);
		Db db;
		if (dbExportType.getUrl().contains("mysql"))
			db = new MysqlHelper();
		else {
			db = new OracleHelper();
		}
		String text = areaTextPanel.getText().trim();
		try {
			db.exeSql(dbExportType.getUrl(), dbExportType.getUserName(), dbExportType.getPass(),text);
		} catch (Exception e1) {
			alert(e1.getMessage());
			return ;
		}
		alert("执行成功！");
		FileUtils.appendContent(dirPath+File.separator+"db.log", text+"\r\n",0);		
	}

	protected void setCurrConn(int index) {
		if(index == -1 ){
			userNameInput.setText("");
			passInput.setText("");
			urlInput.setText("");
			return ;
		}
		this.dbExport = this.dbExports.get(index);
		userNameInput.setText(this.dbExport.getUserName());
		passInput.setText(this.dbExport.getPass());
		urlInput.setText(this.dbExport.getUrl());
	}

	public static void main(String[] args) {
		try {
//			Main.main(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		GradientPaint p2 = new GradientPaint(0, 1,
				new Color(186, 131, 164, 200), 0, 20, new Color(255, 255, 255,
						255));
		g2d.setPaint(p2);
		g2d.drawRoundRect(1, 20, getWidth()-5, 6 * 32, 5, 5);
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
				BasicStroke.JOIN_ROUND)); // 设置新的画刷
		g2d.setFont(new Font("宋体", Font.PLAIN, 12));
		g2d.drawString("数据库", 5, 15);
	}
}

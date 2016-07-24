package sensor;

public abstract class sensor {
	private String _name;
	private String _description;
	private commandBehavior _commandBehavior;
	private dataBehavior _dataBehavior;
	
	abstract public void getData();
	abstract public void sendCommand();
	
	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public String getDescription() {
		return _description;
	}
	
	public void setDescription (String desc) {
		_description = desc;
	}
	
	public commandBehavior getCommandBehavior() {
		return _commandBehavior;
	}
	
	public void setCommandBehavior (commandBehavior cb) {
		_commandBehavior = cb;
	}
	
	public dataBehavior getDataBehavior() {
		return _dataBehavior;
	}
	
	public void setDataBehavior(dataBehavior db) {
		_dataBehavior = db;
	}
	
	public void executeCommand() {
		_commandBehavior.start();
	}
	
	public int [] transferData() {
		int [] data;
		data = _dataBehavior.read(); //transfer();
		return data;
	}
	
}

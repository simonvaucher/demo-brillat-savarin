public class LoginManagerEventsEmitter {
    /// Shared Instance.
    public static var sharedInstance = LoginManagerEventsEmitter()
    
    private static var eventEmitter: LoginManager!
    
    private init() {}
    
    // When React Native instantiates the emitter it is registered here.
    func registerEventEmitter(eventEmitter: LoginManager) {
        LoginManagerEventsEmitter.eventEmitter = eventEmitter
    }
    
    func dispatch(name: String, body: Any?) {
        LoginManagerEventsEmitter.eventEmitter.sendEvent(withName: name, body: body)
    }
    
    // All Events which must be supported by React Native.
    lazy var supportedEvents: [String] = {
        return ["onShow", "onHide"]
    }()
}


import Foundation

public class LoginReactModule: NSObject {  
    static let sharedInstance = LoginReactModule()
    
    var bridge: RCTBridge?
    
    func createBridgeIfNeeded() -> RCTBridge {
        if bridge == nil {
            bridge = RCTBridge.init(delegate: self, launchOptions: nil)
        }
        return bridge!
    }
    
    func viewForModule(_ moduleName: String, initialProperties: [String : Any]?) -> RCTRootView {
        let viewBridge = createBridgeIfNeeded()
        let rootView: RCTRootView = RCTRootView(
            bridge: viewBridge,
            moduleName: moduleName,
            initialProperties: initialProperties)
        return rootView
    }
}

extension LoginReactModule: RCTBridgeDelegate {
    public func sourceURL(for bridge: RCTBridge!) -> URL! {
        if TARGET_OS_SIMULATOR != 0 {
            return RCTBundleURLProvider.sharedSettings().jsBundleURL(forBundleRoot: "index", fallbackResource: nil)
        }
        return URL(string: Bundle.main.infoDictionary?["APDebugPackagerURL"] as! String)!
    }
}


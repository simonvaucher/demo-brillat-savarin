import Foundation
import UIKit

@objc(APReactNativeLogin)
public class LoginManager: RCTEventEmitter  {
    
    static var viewController: LoginViewController?
    
    override init() {
        super.init()
        LoginManagerEventsEmitter.sharedInstance.registerEventEmitter(eventEmitter: self)
    }
    
    // - Returns: all supported events sent from Native to RN
    @objc open override func supportedEvents() -> [String] {
        return LoginManagerEventsEmitter.sharedInstance.supportedEvents
    }
    
    /**
     send login result from RN
     */
    @objc func onLoginResult(_ result: String) {
        DispatchQueue.main.async {
            if let viewController = LoginManager.viewController {
                viewController.onLoginResult(result)
            }
        }
    }
}


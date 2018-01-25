import Foundation
import UIKit

class LoginViewController: UIViewController  {
    
    weak var delegate: LoginManagerDelegate?
    var reactRootView: RCTRootView!  
    var loginManagerEventsEmitter: LoginManagerEventsEmitter!
    var parentController: UIViewController!
    public private(set) var initialized = false
    
    convenience init(withParentController parentController: UIViewController) {
        self.init(nibName:nil, bundle:nil)
        
        self.parentController = parentController
        self.delegate = (parentController as! LoginManagerDelegate)    
        self.modalPresentationStyle = .overCurrentContext
        self.modalTransitionStyle = .crossDissolve
        
        initializeReactNativeView()
    }
    
    func initializeReactNativeView() {
        let reactNativeViewReady: (Notification) -> Void = { notification in
            self.delegate?.onReady()
            self.initialized = true
        }
        NotificationCenter.default.addObserver(
            forName: NSNotification.Name(rawValue: "RCTContentDidAppearNotification"),
            object: nil,
            queue: OperationQueue.main,
            using: reactNativeViewReady)
        
        reactRootView = LoginReactModule.sharedInstance.viewForModule(
            "RNRoot",
            initialProperties: nil)
        reactRootView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        reactRootView.backgroundColor = UIColor.clear    
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        LoginManager.viewController = self
        self.view = reactRootView
    }
    
    func show() {
        print("huh?!?!?")
        if (self.initialized) {
            self.parentController!.present(self, animated: false, completion: nil)
            //            self.loginManagerEventsEmitter.dispatch(name: "onShow", body: nil)
        }
    }
    
    func hide() {
        self.dismiss(animated: false, completion: {
            self.presentingViewController?.dismiss(animated: false, completion: nil);
            //            self.loginManagerEventsEmitter.dispatch(name: "onHide", body: nil)
        });
    }
    
    /**
     receives selected item from react native
     */
    @objc func onLoginResult(_ result: String?) {
        delegate?.onLoginResult(result)
        hide()
    }
}


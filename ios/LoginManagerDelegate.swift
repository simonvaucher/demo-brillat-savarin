public protocol LoginManagerDelegate: class {
    func onLoginResult(_ result: String?)
    func onReady()
}


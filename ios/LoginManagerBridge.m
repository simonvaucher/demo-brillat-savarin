#import <React/RCTBridgeModule.h>
#import "React/RCTEventEmitter.h"

@interface RCT_EXTERN_MODULE(APReactNativeLogin, RCTEventEmitter)

RCT_EXTERN_METHOD(onLoginResult:(nonnull NSString *) result)
RCT_EXTERN_METHOD(supportedEvents)

@end

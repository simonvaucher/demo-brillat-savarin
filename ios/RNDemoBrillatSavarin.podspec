Pod::Spec.new do |s|
  s.name         = "RNDemoBrillatSavarin"
  s.version      = "1.0.0"
  s.summary      = "RNDemoBrillatSavarin"
  s.description  = <<-DESC
                  RNDemoBrillatSavarin
                   DESC
  s.homepage     = ""
  s.license      = "MIT"
  s.author             = { "author" => "s.vaucher@applicaster.com" }
  s.platform     = :ios, "9.0"
  s.source       = { :git => "https://github.com/simonvaucher/demo-brillat-savarin.git", :tag => "master" }
  s.source_files  = "RNDemoBrillatSavarin/**/*.{h,m}"
  s.requires_arc = true

  s.dependency "React"
  #s.dependency "others"

end

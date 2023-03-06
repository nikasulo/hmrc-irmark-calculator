# frozen_string_literal:true

Gem::Specification.new do |s|
  s.name = 'hmrc_ir_mark_calculator'
  s.version = '1.0.0'
  s.authors = ['Dami Olusakin']
  s.date = '2023-03-06'
  s.description = 'IRmark calculator for HMRC Corporate Tax Submissions'
  s.summary = s.description
  s.email = 'projects@oluwadamilareolusakin.com'
  s.files = Dir['README.md'] + Dir['lib/**/*.rb'] + Dir['lib/dependencies/*'] + Dir['spec/**/*.rb']
  s.homepage = 'http://www.github.com/oluwadamilareolusakin/hmrc-irmark-calculator'
end

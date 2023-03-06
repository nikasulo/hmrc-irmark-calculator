# frozen_string_literal: true

require 'open3'
require 'hmrc_ir_mark_calculator/logger'

# A Java wrapper that creates an IRmark from an HMRC submission file
# It accepts the path to the file, which should contain the same contents
# being sent to HMRC, inlcuding the GovTalkMessage, and body
#
# It needs the absolute path to the Full CT600 File that has been prepared
# for submission. Providing a relative path may not work as expected.
class HmrcIrMarkCalculator
  attr_reader :file_path

  extend IrMarkLogger

  def initialize(file_path)
    @file_path = file_path
  end

  def run
    self.class.logger.info("Using #{cmd} to generate IRmark for file #{file_path}")

    create_mark
  rescue StandardError => e
    handle_error(e.message)
  end

  private

  def create_mark
    Open3.popen3(cmd) do |_stdin, stdout, stderr, _wait_thr|
      original_result = stdout.read

      original_result =~ /{(.*)}/
      result = ::Regexp.last_match(1)

      return handle_success(result) unless result.nil?

      error = stderr&.read
      return handle_error(error, original_result)
    end
  end

  def handle_error(error, original_result = '')
    self.class.logger.error(
      "There was an error generating the IRmark for file #{file_path}. #{error} #{original_result}"
    )
    { error: error, success: false }
  end

  def handle_success(result)
    self.class.logger.info("Output of the IRmark for file #{file_path} is #{result}")

    { result: result, success: true }
  end

  def cmd
    "#{change_directory} && #{set_env!} && #{ir_mark}"
  end

  def set_env!
    "export CLASSPATH='dependencies/jce-jdk13-114.jar:dependencies/xmlsec-1.4.1.jar:"\
      "dependencies/xalan_enhanced.jar:dependencies/commons-logging-1.1.1.jar:dependencies/markcalc.jar'"
  end

  def ir_mark
    "java uk.gov.hmrc.mark.IRMarkCalculator #{file_path}"
  end

  def change_directory
    "cd #{dir}"
  end

  def dir
    __dir__
  end
end

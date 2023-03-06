# frozen_string_literal: true

require 'logger'

# Logging for the calculator
# Provides an escape hatch to set a custom logger
# otherwise the default ruby logger will be used
#
module IrMarkLogger
  attr_writer :logger

  def logger
    @logger ||= Logger.new($stdout).tap do |log|
      log.progname = name
    end
  end
end
